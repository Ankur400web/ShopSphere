package com.e_commerce.ShopSphere.auth.service;


import com.e_commerce.ShopSphere.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import com.e_commerce.ShopSphere.auth.entity.RefreshToken;
import com.e_commerce.ShopSphere.user.entity.User;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private String generateToken(){
        byte[] randomByte = new byte[32];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomByte);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomByte);
    }

    private String hashingToken(String token){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8)
            );

            return Base64.getEncoder()
                    .encodeToString(hash);
        } catch (NoSuchAlgorithmException e){
            throw new IllegalStateException("SHA-256 algorithm is not available", e);
        }
    }

    private String createRefreshToken(User user){
        String rawToken = generateToken();
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setTokenHash(hashingToken(rawToken));
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(
                OffsetDateTime.now().plusDays(30)
        );

        refreshTokenRepository.save(refreshToken);

        return rawToken;
    }
}
