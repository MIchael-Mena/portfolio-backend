package com.portfolioCRUD.portfolio.security.dto;

import com.portfolioCRUD.portfolio.security.entity.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Collection<? extends GrantedAuthority> roles;
    private List<Api> apiKeys;

}
