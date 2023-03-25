package com.github.mcnulty2.timesaver.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@PropertySource("classpath:sciforma.properties")
@ConfigurationProperties(prefix = "sciforma")
@Configuration
public class SciformaData {
    private String url;
    private String user;
    private String password;
}
