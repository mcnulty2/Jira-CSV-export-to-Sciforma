package com.github.mcnulty2.timesaver;

import com.codeborne.selenide.Configuration;
import com.github.mcnulty2.timesaver.containers.DatesContainer;
import com.github.mcnulty2.timesaver.containers.HoursContainer;
import com.github.mcnulty2.timesaver.containers.IssueContainer;
import com.github.mcnulty2.timesaver.containers.LanguageContainer;
import com.github.mcnulty2.timesaver.containers.LoginContainer;
import com.github.mcnulty2.timesaver.data.EnumTranslations;
import com.github.mcnulty2.timesaver.data.JiraBean;
import com.github.mcnulty2.timesaver.data.ProjectMappingConfig;
import com.github.mcnulty2.timesaver.data.SciformaConfig;
import com.github.mcnulty2.timesaver.helper.EntryFilterHelper;
import com.github.mcnulty2.timesaver.processing.CsvParser;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
@Component
public class SelenideImporter {
    private final SciformaConfig sciformaConfig;
    private final CsvParser csvParser;
    private final ProjectMappingConfig projectMappingConfig;

    public SelenideImporter(SciformaConfig sciformaConfig, CsvParser csvParser, ProjectMappingConfig projectMappingConfig) {
        this.sciformaConfig = sciformaConfig;
        this.csvParser = csvParser;
        this.projectMappingConfig = projectMappingConfig;

        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
    }

    public void importDataIntoSciforma(File file) throws Exception {
        List<JiraBean> list = csvParser.parseFile(file);
        EntryFilterHelper filter = new EntryFilterHelper(list);
        open(sciformaConfig.getUrl());
        Thread.sleep(2000);
        LanguageContainer.setLanguage(sciformaConfig.getLanguage());
        Thread.sleep(5000);
        LoginContainer.login(sciformaConfig.getUser(), sciformaConfig.getPassword());
        $(By.partialLinkText(EnumTranslations.TIMESHEET.getText(sciformaConfig.getLanguage()))).click();
        Thread.sleep(10000);
        sciformaConfig.setDates(DatesContainer.readDates(sciformaConfig.getLanguage()));
        projectMappingConfig.getUniqueProjects().forEach(
                p -> {
                    HoursContainer.logHoursForProject(p, filter.getWeeklyTimes(p, sciformaConfig.getDates(), sciformaConfig.getLocaleDecimalSeparator()));
                    IssueContainer.logIssuesForProject(p, filter.getWeeklyIssues(p, sciformaConfig.getDates(), sciformaConfig.getDelimiter()), sciformaConfig.getDates(), sciformaConfig.getLanguage());
                }
        );
        Thread.sleep(100000);
    }
}
