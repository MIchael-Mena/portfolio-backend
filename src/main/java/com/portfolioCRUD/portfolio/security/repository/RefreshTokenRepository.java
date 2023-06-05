package com.portfolioCRUD.portfolio.security.repository;

import com.portfolioCRUD.portfolio.security.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("DELETE FROM refresh_token rt WHERE rt.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    @Modifying
    void deleteByToken(String token);
}
