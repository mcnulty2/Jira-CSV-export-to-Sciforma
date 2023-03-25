package com.github.mcnulty2.timesaver;

import com.github.mcnulty2.timesaver.exception.JiraToSciformaException;
import com.github.mcnulty2.timesaver.importer.SelenideImporter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ImportJiraTimesheetApplication implements CommandLineRunner {
    private static final String CSV = ".csv";
    private final SelenideImporter importer;

    public ImportJiraTimesheetApplication(SelenideImporter importer) {
        this.importer = importer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ImportJiraTimesheetApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0 || args[0] == null || !args[0].contains(CSV)) {
            throw new JiraToSciformaException("The JIRA export CSV file is expected as a run parameter.");
        } else {
            File file = new File(args[0]);
            if (!file.exists()) {
                throw new JiraToSciformaException("The JIRA export CSV file was not found.");
            } else {
                importer.importDataIntoSciforma(file);
            }
        }
    }
}
