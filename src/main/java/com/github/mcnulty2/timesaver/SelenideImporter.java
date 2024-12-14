package com.github.mcnulty2.timesaver;

import com.codeborne.selenide.Configuration;
import com.github.mcnulty2.timesaver.containers.DatesContainer;
import com.github.mcnulty2.timesaver.containers.HoursContainer;
import com.github.mcnulty2.timesaver.containers.IssueContainer;
import com.github.mcnulty2.timesaver.containers.LanguageContainer;
import com.github.mcnulty2.timesaver.containers.LoginContainer;
import com.github.mcnulty2.timesaver.containers.WeekContainer;
import com.github.mcnulty2.timesaver.data.EnumTranslations;
import com.github.mcnulty2.timesaver.data.JiraData;
import com.github.mcnulty2.timesaver.data.ProjectMappingConfig;
import com.github.mcnulty2.timesaver.data.SciformaConfig;
import com.github.mcnulty2.timesaver.helper.EntryFilterHelper;
import com.github.mcnulty2.timesaver.processing.CsvParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
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
        Configuration.timeout = 60000;
        Configuration.browser = sciformaConfig.getBrowser();
        if (Configuration.browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            Configuration.browserCapabilities = options;
        }
    }

    public void importDataIntoSciforma(File file) throws Exception {
        // Prepare Config
        List<JiraData> list = csvParser.parseFile(file);
        EntryFilterHelper filter = new EntryFilterHelper(list);

        // Start Test
        open(sciformaConfig.getUrl());
        Thread.sleep(2000);
        LoginContainer.login(sciformaConfig.getUser(), sciformaConfig.getPassword());
        Thread.sleep(10000);
        sciformaConfig.setLocale(LanguageContainer.detectLocale());
        $(By.partialLinkText(EnumTranslations.TIMESHEET.getText(sciformaConfig.getLanguage()))).click();
        WeekContainer.selectWeek(sciformaConfig.getWeek());
        sciformaConfig.setDates(DatesContainer.readDates(sciformaConfig.getLanguage(), sciformaConfig.getWeek()));
        for (String project: projectMappingConfig.getUniqueProjects()) {
            HoursContainer.logHoursForProject(project, filter.getWeeklyTimes(project, sciformaConfig.getDates(), sciformaConfig.getLocaleDecimalSeparator()));
            List<String> weeklyIssuesForProject = filter.getWeeklyIssues(project, sciformaConfig.getDates(), sciformaConfig.getDelimiter());
            if (isNotEmpty(weeklyIssuesForProject)) {
                IssueContainer.logIssuesForProject(project, weeklyIssuesForProject, sciformaConfig.getDates(), sciformaConfig.getLanguage());
            }
            Thread.sleep(2000);
        }
        Thread.sleep(200000);
    }

    private boolean isNotEmpty(List<String> weeklyIssues) {
        return weeklyIssues.stream().anyMatch(weekDay -> StringUtils.isNotEmpty(weekDay));
    }
}
