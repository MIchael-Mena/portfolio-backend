package com.portfolioCRUD.portfolio.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponse {

    private Long id;
    private String username;
    private String email;

    public UserResponse(String username, String email, Long id) {
        this.username = username;
        this.email = email;
        this.id = id;
    }
}
