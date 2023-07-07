package com.projx.blogapplication.models;

import java.util.Date;

import com.projx.blogapplication.models.enums.Role;

public record UserDto(
        Long id,
        String name,
        String username,
        String email,
        Role role,
        Date createdOn
) {
}
