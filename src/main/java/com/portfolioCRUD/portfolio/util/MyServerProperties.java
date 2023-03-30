package com.portfolioCRUD.portfolio.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "server")
public class MyServerProperties {

    private String domain;
    private String port;


}
