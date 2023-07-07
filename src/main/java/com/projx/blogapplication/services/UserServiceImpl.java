package com.projx.blogapplication.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projx.blogapplication.exceptions.StatusException;
import com.projx.blogapplication.models.AppUser;
import com.projx.blogapplication.models.UserDto;
import com.projx.blogapplication.models.entities.User;
import com.projx.blogapplication.models.enums.Role;
import com.projx.blogapplication.models.payloads.SignInRequest;
import com.projx.blogapplication.models.payloads.SignUpRequest;
import com.projx.blogapplication.repositories.interfaces.UserRepository;
import com.projx.blogapplication.services.interfaces.UserService;

import java.util.Random;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto registerUser(SignUpRequest signup) {
        log.info("Registering user with {}", signup.getEmail());
        boolean isEmailExist = userRepository.existsUserByEmailIgnoreCase(
                signup.getEmail()
        );
        if (isEmailExist) {
            throw new StatusException("Email already exist.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        // Generating unique username
        String username = signup.getName().replace(" ", "")
                .toLowerCase()
                .concat(String.valueOf(new Random().nextInt(1, Integer.MAX_VALUE)));
        while (userRepository.existsUserByUsernameIgnoreCase(username)) {
            username = signup.getName().replace(" ", "")
                    .toLowerCase()
                    .concat(String.valueOf(new Random().nextInt(1, Integer.MAX_VALUE)));
        }
        User user = new User(
                signup.getName(),
                username,
                signup.getEmail(),
                passwordEncoder.encode(signup.getPassword()),
                Role.USER
        );

        user = userRepository.save(user);

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedOn()
        );
    }

    @Override
    public Long signInUser(SignInRequest signIn) {
        log.info("Sign in with {}", signIn.getUsername());
        AppUser userDetails = null;
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signIn.getUsername(),
                            signIn.getPassword()
                    )
            );
            userDetails = (AppUser) authenticate.getPrincipal();
        } catch (Exception exception) {
            throw new StatusException("Invalid login attempt.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return userDetails.getUser().getId();
    }

    @Override
    public UserDto getById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new StatusException("Invalid user.", HttpStatus.UNPROCESSABLE_ENTITY));
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedOn()
        );
    }

}
