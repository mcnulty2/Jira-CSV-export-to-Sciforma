package com.github.mcnulty2.timesaver.processing;

import com.github.mcnulty2.timesaver.data.JiraColumnMappingConfig;
import com.github.mcnulty2.timesaver.data.JiraData;
import com.github.mcnulty2.timesaver.data.ProjectMappingConfig;
import com.github.mcnulty2.timesaver.exception.JiraToSciformaException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CsvParser {
    private final JiraColumnMappingConfig jiraColumnMappingConfig;
    private final ProjectMappingConfig projectMappingConfig;

    public CsvParser(JiraColumnMappingConfig jiraColumnMappingConfig,
                     ProjectMappingConfig projectMappingConfig) {
        this.jiraColumnMappingConfig = jiraColumnMappingConfig;
        this.projectMappingConfig = projectMappingConfig;
    }

    public List<JiraData> parseFile(File file) throws Exception {
        List<JiraData> list = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            reader.readLine();
            log.info("Project Map: {}", projectMappingConfig.getProjectMap());
            while(reader.ready()) {
                String line = reader.readLine();
                line = line.startsWith("\"") ? line.replaceFirst("\"", "") : line;
                String[] columns = line.split("\"" + jiraColumnMappingConfig.getSeparator() + "\"");
                JiraData jiraData = new JiraData();
                jiraData.setIssue(columns[jiraColumnMappingConfig.getIssue()]);
                jiraData.setTime(new BigDecimal(columns[jiraColumnMappingConfig.getTime()]));
                jiraData.setDate(LocalDate.parse(columns[jiraColumnMappingConfig.getDate()].substring(0, 10)));
                log.info("Issue: {}, Component: {}", columns[jiraColumnMappingConfig.getIssue()], columns[jiraColumnMappingConfig.getProject()]);
                String project = projectMappingConfig.getProjectMap().get(columns[jiraColumnMappingConfig.getProject()]);
                if (StringUtils.isEmpty(project)) {
                    throw new JiraToSciformaException("Project column not set for: " + jiraData.getIssue());
                } else {
                    jiraData.setProject(projectMappingConfig.getProjectMap().get(columns[jiraColumnMappingConfig.getProject()]));
                }
                list.add(jiraData);
            }
        }
        return list;
    }

}
