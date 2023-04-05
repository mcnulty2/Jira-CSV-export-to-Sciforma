package com.github.mcnulty2.timesaver.processing;

import com.github.mcnulty2.timesaver.data.JiraBean;
import com.github.mcnulty2.timesaver.data.JiraColumnMappingConfig;
import com.github.mcnulty2.timesaver.data.ProjectMappingConfig;
import com.github.mcnulty2.timesaver.exception.JiraToSciformaException;
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
public class CsvParser {
    private final JiraColumnMappingConfig jiraColumnMappingConfig;
    private final ProjectMappingConfig projectMappingConfig;

    public CsvParser(JiraColumnMappingConfig jiraColumnMappingConfig,
                     ProjectMappingConfig projectMappingConfig) {
        this.jiraColumnMappingConfig = jiraColumnMappingConfig;
        this.projectMappingConfig = projectMappingConfig;
    }

    public List<JiraBean> parseFile(File file) throws Exception {
        List<JiraBean> list = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            reader.readLine();
            while(reader.ready()) {
                String line = reader.readLine();
                line = line.startsWith("\"") ? line.replaceFirst("\"", "") : line;
                String[] columns = line.split("\"" + jiraColumnMappingConfig.getSeparator() + "\"");
                JiraBean jiraBean = new JiraBean();
                jiraBean.setIssue(columns[jiraColumnMappingConfig.getIssue()]);
                jiraBean.setTime(new BigDecimal(columns[jiraColumnMappingConfig.getTime()]));
                jiraBean.setDate(LocalDate.parse(columns[jiraColumnMappingConfig.getDate()].substring(0, 10)));
                String project = projectMappingConfig.getProjectMap().get(columns[jiraColumnMappingConfig.getProject()]);
                if (StringUtils.isEmpty(project)) {
                    throw new JiraToSciformaException("Project column not set for: " + jiraBean.getIssue());
                } else {
                    jiraBean.setProject(projectMappingConfig.getProjectMap().get(columns[jiraColumnMappingConfig.getProject()]));
                }
                list.add(jiraBean);
            }
        }
        return list;
    }

}
