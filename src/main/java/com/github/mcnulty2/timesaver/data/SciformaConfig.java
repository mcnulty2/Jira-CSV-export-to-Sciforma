package com.github.mcnulty2.timesaver.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@PropertySource("classpath:sciforma.properties")
@ConfigurationProperties(prefix = "sciforma")
@Configuration
public class SciformaConfig {
    private enum SubmitType {AUTO, MANUAL};

    private String url;
    private String user;
    private String password;
    private String delimiter;
    private SubmitType submit;
    private String locale;
    private List<LocalDate> dates;
    private String browser;

    public String getLanguage() {
        return locale.substring(0, 2);
    }

    public String getLocaleDecimalSeparator() {
        if (locale.contains("_us")) {
            return ".";
        } else {
            return ",";
        }
    }
}
