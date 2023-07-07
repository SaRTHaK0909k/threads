package com.projx.blogapplication.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projx.blogapplication.models.UserDto;
import com.projx.blogapplication.services.interfaces.UserService;
import com.projx.blogapplication.utils.Helper;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getProfile() {
        Long userId = Helper.getLoggedInUserDetails().getUser().getId();
        UserDto user = userService.getById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(
            @PathVariable(name = "userId") Long userId
    ) {
        UserDto user = userService.getById(userId);
        return ResponseEntity.ok(user);
    }
}
