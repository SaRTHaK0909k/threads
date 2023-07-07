package com.projx.blogapplication.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projx.blogapplication.models.enums.TokenType;
import com.projx.blogapplication.models.payloads.SignInRequest;
import com.projx.blogapplication.models.payloads.SignUpRequest;
import com.projx.blogapplication.services.CookieService;
import com.projx.blogapplication.services.interfaces.TokenService;
import com.projx.blogapplication.services.interfaces.UserService;
import com.projx.blogapplication.utils.Constants;
import com.projx.blogapplication.utils.Helper;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticationController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.registerUser(signUpRequest);
        return ResponseEntity.ok("Account created successfully.");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest, HttpServletResponse httpServletResponse) {
        Long userId = userService.signInUser(signInRequest);
        CookieService.add(httpServletResponse, Constants.REFRESH_TOKEN, tokenService.generateRefreshToken(userId), -1);
        CookieService.add(httpServletResponse, Constants.ACCESS_TOKEN, tokenService.generateAccessToken(userId), -1);
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut(@CookieValue(name = Constants.REFRESH_TOKEN) String refreshToken, HttpServletResponse httpServletResponse) {
        Long userId = Helper.getLoggedInUserDetails().getUser().getId();
        tokenService.deleteToken(refreshToken, TokenType.REFRESH_TOKEN, userId);
        CookieService.add(httpServletResponse, Constants.ACCESS_TOKEN, null, 0);
        CookieService.add(httpServletResponse, Constants.REFRESH_TOKEN, null, 0);
        return ResponseEntity.ok("Logout successfully.");
    }
}
