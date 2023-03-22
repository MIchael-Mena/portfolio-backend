package com.portfolioCRUD.portfolio.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class LoginUser {

/*    @NotBlank
    private String userName;*/
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
