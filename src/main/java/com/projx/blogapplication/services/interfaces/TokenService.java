package com.projx.blogapplication.services.interfaces;

import com.projx.blogapplication.models.enums.TokenType;

public interface TokenService {
    String generateAccessToken(Long userId);

    String generateRefreshToken(Long userId);

    boolean isTokenValid(String token, TokenType type, Long userId);

    void deleteToken(String token, TokenType tokenType, Long userId);
}
