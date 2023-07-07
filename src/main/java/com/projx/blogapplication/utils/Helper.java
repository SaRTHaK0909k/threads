package com.projx.blogapplication.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.projx.blogapplication.models.AppUser;

public class Helper {

    public static AppUser getLoggedInUserDetails() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appUser;
    }

}
