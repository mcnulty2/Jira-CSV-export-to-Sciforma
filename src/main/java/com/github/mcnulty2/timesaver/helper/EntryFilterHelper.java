package com.github.mcnulty2.timesaver.helper;

import com.github.mcnulty2.timesaver.data.JiraData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntryFilterHelper {
    private final List<JiraData> list;

    public EntryFilterHelper(List<JiraData> list) {
        this.list = list;
    }

    public BigDecimal getTime(String project, LocalDate date) {
        return filterByDateAndProject(project, date)
                .map(e -> e.getTime())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getList(String project, LocalDate date, String delimiter) {
        return filterByDateAndProject(project, date)
                .map(e -> e.getIssue())
                .distinct()
                .collect(Collectors.joining(delimiter));
    }

    public List<String> getWeeklyTimes(String project, List<LocalDate> week, String decimalSeparator) {
        List<String> weeklyTimes = new ArrayList<>();
        week.forEach(d -> {
            String time = getTime(project, d).setScale(2, RoundingMode.HALF_UP).toString();
            weeklyTimes.add(time.replace(".", decimalSeparator));
        });
        return weeklyTimes;
    }

    public List<String> getWeeklyIssues(String project, List<LocalDate> week, String delimiter) {
        List<String> weeklyIssues = new ArrayList<>();
        week.forEach(d -> {
            String dailyIssues = getList(project, d, delimiter);
            weeklyIssues.add(dailyIssues);
        });
        return weeklyIssues;
    }

    private Stream<JiraData> filterByDateAndProject(String project, LocalDate date) {
        return list.stream()
                .filter(jiraDataEntry -> jiraDataEntry.getDate().equals(date))
                .filter(jiraDataEntry -> jiraDataEntry.getProject().equals(project));
    }
}
