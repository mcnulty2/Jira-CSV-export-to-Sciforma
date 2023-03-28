package com.github.mcnulty2.timesaver.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

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
        Resource resource = new ClassPathResource(PROJECT_MAPPING_PROPERTIES);
        Properties projectProps = new Properties();
        projectProps.load(resource.getInputStream());
        projectMap = (Map)projectProps;
        uniqueProjects = projectMap.values().stream()
                .distinct().collect(Collectors.toList());
    }
}
