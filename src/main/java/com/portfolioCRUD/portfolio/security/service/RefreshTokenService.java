package com.portfolioCRUD.portfolio.security.service;

import com.portfolioCRUD.portfolio.security.entity.RefreshToken;
import com.portfolioCRUD.portfolio.security.entity.User;
import com.portfolioCRUD.portfolio.security.repository.RefreshTokenRepository;
import com.portfolioCRUD.portfolio.security.repository.UserRepository;
import com.portfolioCRUD.portfolio.security.exception.TokenRefreshException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs * 1000));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signing request");
        }
        return token;
    }


    @Transactional
    public void deletePreviousRefreshTokens(User user) {
        // Elimina todos los refresh tokens de un usuario
        refreshTokenRepository.deleteAllByUserId(user.getId());
    }

    @Transactional
    public void deleteByToken(String token) {
        // Elimina un refresh token
        refreshTokenRepository.deleteByToken(token);
    }

}
