package com.github.mcnulty2.timesaver.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Getter
@Setter
@Configuration
public class ProjectMappingConfig {
    private static final String PROJECT_MAPPING_PROPERTIES = "project_mapping.properties";
    private Map<String, String> projectMap;
    private List<String> uniqueProjects;

    public ProjectMappingConfig() throws IOException {
        String propertiesPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                + PROJECT_MAPPING_PROPERTIES;
        Properties projectProps = new Properties();
        projectProps.load(new FileInputStream(propertiesPath));
        projectMap = (Map)projectProps;
        uniqueProjects = projectMap.values().stream()
                .distinct().collect(Collectors.toList());
    }
}
