package com.e_commerce.ShopSphere.auth.service;

import com.e_commerce.ShopSphere.auth.dto.LoginRequest;
import com.e_commerce.ShopSphere.auth.dto.LoginResponse;
import com.e_commerce.ShopSphere.auth.entity.RefreshToken;
import com.e_commerce.ShopSphere.auth.security.JwtService;
import com.e_commerce.ShopSphere.user.entity.User;
import com.e_commerce.ShopSphere.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + request.getEmail()
                        )
                );
        String refreshToken = refreshTokenService.createRefreshToken(user);

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().getName()
        );



        return new LoginResponse(
                token,
                refreshToken,
                "Bearer",
                user.getRole().getId(),
                user.getEmail(),
                user.getRole().getName(),
                user.getFirstName()
        );
    }

    public LoginResponse refreshAccessToken(String rawRefreshToken) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(rawRefreshToken);

        User user = refreshToken.getUser();

        String accessToken = jwtService.generateAccessToken(
                user.getEmail(),
                user.getRole().getName()
        );

        return new LoginResponse(
                accessToken,
                rawRefreshToken,
                "Bearer",
                user.getRole().getId(),
                user.getEmail(),
                user.getRole().getName(),
                user.getFirstName()
        );
    }
}