package com.github.mcnulty2.timesaver.helper;

import com.github.mcnulty2.timesaver.data.JiraBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntryFilterHelper {
    private final List<JiraBean> list;

    public EntryFilterHelper(List<JiraBean> list) {
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
                .collect(Collectors.joining(delimiter));
    }

    public List<BigDecimal> getWeeklyTimes(String project, List<LocalDate> week) {
        List<BigDecimal> weeklyTimes = new ArrayList<>();
        week.forEach(d -> weeklyTimes.add(getTime(project, d).setScale(2, RoundingMode.HALF_UP)));
        return weeklyTimes;
    }

    private Stream<JiraBean> filterByDateAndProject(String project, LocalDate date) {
        return list.stream()
                .filter(jiraBeanEntry -> jiraBeanEntry.getDate().equals(date))
                .filter(jiraBeanEntry -> jiraBeanEntry.getProject().equals(project));
    }
}
