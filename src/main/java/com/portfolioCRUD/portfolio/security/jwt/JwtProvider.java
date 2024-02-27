package com.portfolioCRUD.portfolio.security.jwt;

import com.portfolioCRUD.portfolio.security.entity.MainUser;
import com.portfolioCRUD.portfolio.security.exception.InvalidJwtException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        MainUser user = (MainUser) authentication.getPrincipal();
        return Jwts.builder().setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000 ))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000 ))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token mal formado");
//            throw new InvalidJwtException("El token proporcionado no es válido o está mal formado", HttpStatus.BAD_REQUEST.value());
        } catch (UnsupportedJwtException e) {
            logger.error("Token no soportado");
//            throw new InvalidJwtException("El token no es compatible", HttpStatus.BAD_REQUEST.value());
        } catch (IllegalArgumentException e) {
            logger.error("Token vacío");
//            throw new InvalidJwtException("El token está va cío", HttpStatus.BAD_REQUEST.value());
        } catch (SignatureException e) {
            logger.error("Fallo en la firma");
//            throw new InvalidJwtException("Fallo en la firma del token", HttpStatus.UNAUTHORIZED.value());
        } catch (ExpiredJwtException e) {
            logger.error("Token expirado");
//            throw new InvalidJwtException("El token ha expirado", HttpStatus.UNAUTHORIZED.value());
        }
        return false;
    }

}
