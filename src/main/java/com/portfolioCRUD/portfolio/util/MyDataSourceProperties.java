package com.portfolioCRUD.portfolio.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class MyDataSourceProperties {

    private String url;
    private String username;
    private String password;


}
