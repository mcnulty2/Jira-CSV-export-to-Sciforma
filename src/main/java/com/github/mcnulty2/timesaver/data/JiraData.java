package com.github.mcnulty2.timesaver.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@PropertySource("classpath:jira.properties")
@ConfigurationProperties(prefix = "jira.column")
@Configuration
public class JiraData {
    private String issue;
    private String time;
    private String date;
    private String project;
}

