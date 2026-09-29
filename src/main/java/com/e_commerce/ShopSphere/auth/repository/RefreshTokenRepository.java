package com.e_commerce.ShopSphere.auth.repository;

import com.e_commerce.ShopSphere.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    Optional<RefreshToken> findByTokenHashAndRevokedAtIsNull(String tokenHash);
}