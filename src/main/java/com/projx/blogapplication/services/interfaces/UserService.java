package com.projx.blogapplication.services.interfaces;

import com.projx.blogapplication.models.UserDto;
import com.projx.blogapplication.models.payloads.SignInRequest;
import com.projx.blogapplication.models.payloads.SignUpRequest;

public interface UserService {
    UserDto registerUser(SignUpRequest signup);

    Long signInUser(SignInRequest signIn);

    UserDto getById(Long userId);
}
