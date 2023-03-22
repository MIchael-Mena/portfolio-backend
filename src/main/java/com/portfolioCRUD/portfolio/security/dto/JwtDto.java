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
    private String userName;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String accessToken, String refreshToken, String userName, Collection<? extends GrantedAuthority> authorities) {
        this.accessToken = accessToken;
        this.userName = userName;
        this.authorities = authorities;
        this.refreshToken = refreshToken;
    }

}
