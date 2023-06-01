package com.github.mcnulty2.timesaver.containers;

import com.github.mcnulty2.timesaver.data.EnumTranslations;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class DatesContainer {

    private DatesContainer() {
        // No public Constructor
    }

    public static List<LocalDate> readDates(String language) {
        List<LocalDate> week = new ArrayList<>();
        String monday = $(By.xpath("//div[contains(text(), '" + EnumTranslations.TOTAL.getText(language) + "')]"))
                .parent().preceding(6).innerText().substring(3);
        LocalDate mondayDate = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        week.add(mondayDate);
        week.add(mondayDate.plusDays(1));
        week.add(mondayDate.plusDays(2));
        week.add(mondayDate.plusDays(3));
        week.add(mondayDate.plusDays(4));
        week.add(mondayDate.plusDays(5));
        week.add(mondayDate.plusDays(6));
        return week;
    }
}
