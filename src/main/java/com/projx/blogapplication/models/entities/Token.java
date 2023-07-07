package com.projx.blogapplication.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.projx.blogapplication.models.enums.TokenType;

import java.util.Date;

@Entity
@Table(name = "tokens")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TokenType type;
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private Date expiredOn;

    public Token(TokenType type, String token, User user, Date expiredOn) {
        this.type = type;
        this.token = token;
        this.user = user;
        this.expiredOn = expiredOn;
    }
}

