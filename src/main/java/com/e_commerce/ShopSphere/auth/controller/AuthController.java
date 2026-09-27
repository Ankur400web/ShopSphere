package com.e_commerce.ShopSphere.auth.controller;

import com.e_commerce.ShopSphere.auth.dto.LoginRequest;
import com.e_commerce.ShopSphere.auth.dto.LoginResponse;
import com.e_commerce.ShopSphere.auth.dto.MeResponse;
import com.e_commerce.ShopSphere.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> getCurrentUser(
            Authentication authentication
    ) {

        String email = authentication.getName();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElse("UNKNOWN");

        return ResponseEntity.ok(
                new MeResponse(email, role)
        );
    }
}