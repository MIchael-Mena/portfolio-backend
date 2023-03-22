package com.portfolioCRUD.portfolio.security.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter @Setter
public class JwtDto {

    private String accessToken;
    private String refreshToken;
    private String bearer = "Bearer";
    private UserResponse user;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String accessToken, String refreshToken, UserResponse userRes, Collection<? extends GrantedAuthority> authorities) {
        this.accessToken = accessToken;
        this.authorities = authorities;
        this.refreshToken = refreshToken;
        this.user = userRes;
    }

}
