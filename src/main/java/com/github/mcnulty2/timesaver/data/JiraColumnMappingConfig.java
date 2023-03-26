package com.github.mcnulty2.timesaver.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@PropertySource("classpath:jira_column.properties")
@ConfigurationProperties(prefix = "jira.column")
@Configuration
public class JiraColumnMappingConfig {
    private Integer issue;
    private Integer time;
    private Integer date;
    private Integer project;

    private String separator;
}

