package com.github.mcnulty2.timesaver;

import com.github.mcnulty2.timesaver.exception.JiraToSciformaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("Software written by Martin McNulty");
        String classPath = getClass().getResource("ImportJiraTimesheetApplication.class").toString();
        if (!classPath.startsWith("jar")) {
            startFromIDE();
        } else {
            startFromExecutableJar(args);
        }
    }

    private void startFromIDE() throws Exception {
        URL url = ImportJiraTimesheetApplication.class.getResource("/jira_csv");
        if (url == null) {
            throw new JiraToSciformaException("Please place the csv file you want to import in the folder jira_csv.");
        }
        Path dirPath = Paths.get(url.toURI());
        List<Path> paths = Files.list(dirPath)
                .filter(p -> p.toString().endsWith(".csv"))
                .collect(Collectors.toList());
        if (paths.size() > 1) {
            log.info("Several csv files have been found in the folder jira_csv. The most recent will be used.");
        }
        Optional<Path> pathOfMostRecentFile = paths.stream()
                .peek(p -> log.info(p + " " + p.toFile().lastModified()))
                .max(Comparator.comparing(p -> p.toFile().lastModified()));
        File mostRecentFile = pathOfMostRecentFile.get().toFile();
        log.info("Using file: {}", mostRecentFile);
        importer.importDataIntoSciforma(mostRecentFile);
    }

    private void startFromExecutableJar(String[] args) throws Exception {
        URL url = ImportJiraTimesheetApplication.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = url.toString().replace("jar:file:", "").split("!")[0];
        Path dirPath = Paths.get(new File(jarPath).getParent());
        List<Path> paths = Files.list(dirPath)
                .filter(p -> p.toString().endsWith(".csv"))
                .collect(Collectors.toList());
        if (paths.size() == 0) {
            throw new JiraToSciformaException("Please place the csv file you want to import in the same folder as the jar.");
        } else if (paths.size() > 1) {
            log.info("Several csv files have been found in the folder. The most recent will be used.");
        }
        Optional<Path> pathOfMostRecentFile = paths.stream()
                .max(Comparator.comparing(p -> p.toFile().lastModified()));
        File mostRecentFile = pathOfMostRecentFile.get().toFile();
        log.info("Using file: {}", mostRecentFile);
        importer.importDataIntoSciforma(mostRecentFile);
    }
}
