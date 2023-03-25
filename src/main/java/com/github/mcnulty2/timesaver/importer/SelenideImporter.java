package com.github.mcnulty2.timesaver.importer;

import com.codeborne.selenide.Configuration;
import com.github.mcnulty2.timesaver.data.JiraData;
import com.github.mcnulty2.timesaver.data.ProjectMappingData;
import com.github.mcnulty2.timesaver.data.SciformaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class SelenideImporter {
    private final SciformaData sciformaData;
    private final ProjectMappingData projectMappingData;
    private final JiraData jiraData;

    public SelenideImporter(SciformaData sciformaData,
                            ProjectMappingData projectMappingData,
                            JiraData jiraData) {
        this.sciformaData = sciformaData;
        this.projectMappingData = projectMappingData;
        this.jiraData = jiraData;

        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
    }

    public void importDataIntoSciforma(File file) {


        //LoginContainer.login(sciformaData.getUrl(), sciformaData.getUser(), sciformaData.getPassword());
        log.info(jiraData.getIssue());
    }
}
