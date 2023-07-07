package com.projx.blogapplication.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.projx.blogapplication.exceptions.StatusException;
import com.projx.blogapplication.models.entities.Token;
import com.projx.blogapplication.models.entities.User;
import com.projx.blogapplication.models.enums.TokenType;
import com.projx.blogapplication.repositories.interfaces.TokenRepository;
import com.projx.blogapplication.repositories.interfaces.UserRepository;
import com.projx.blogapplication.services.interfaces.TokenService;
import com.projx.blogapplication.utils.JwtProvider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public TokenServiceImpl(TokenRepository tokenRepository, UserRepository userRepository, JwtProvider jwtProvider) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String generateAccessToken(Long userId) {
        log.info("Generating access token for user {}", userId);
        User user = getUser(userId);
        return jwtProvider.doGenerateToken(user);
    }

    @Override
    public String generateRefreshToken(Long userId) {
        log.info("Generating refresh token for user {}", userId);
        User user = getUser(userId);
        Token token = new Token(TokenType.REFRESH_TOKEN, UUID.randomUUID().toString(), user, Date.from(Instant.now().plus(7, ChronoUnit.DAYS)));
        token = tokenRepository.save(token);
        return token.getToken();
    }

    @Override
    public boolean isTokenValid(String token, TokenType type, Long userId) {
        Token validToken = tokenRepository.findTokenByTypeAndUser(token, type, userId)
                .orElse(null);
        return validToken != null && validToken.getExpiredOn().after(new Date(System.currentTimeMillis()));
    }

    @Override
    public void deleteToken(String token, TokenType tokenType, Long userId) {
        tokenRepository.findTokenByTypeAndUser(token, tokenType, userId)
                .ifPresent(validToken -> {
                    tokenRepository.delete(validToken);
                });
    }


    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new StatusException("Invalid user details.", HttpStatus.UNPROCESSABLE_ENTITY));

    }
}
