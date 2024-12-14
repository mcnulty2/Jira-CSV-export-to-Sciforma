package com.github.mcnulty2.timesaver.containers;

import com.github.mcnulty2.timesaver.data.EnumTranslations;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class DatesContainer {

    private DatesContainer() {
        // No public Constructor
    }

    public static List<LocalDate> readDates(String language, String week) {
        List<LocalDate> dates = new ArrayList<>();
        String monday = $(By.xpath("//div[contains(text(), '" + EnumTranslations.TOTAL.getText(language) + "')]"))
                .parent().preceding(6).innerText().substring(3);
        Month mondayMonth = LocalDate.now().getMonth();
        if (!week.contains("+") && LocalDate.now().getDayOfMonth() < Integer.valueOf(monday.trim())) {
            mondayMonth = LocalDate.now().getMonth().minus(1);
        }
        LocalDate mondayDate = LocalDate.of(LocalDate.now().getYear(), mondayMonth, Integer.valueOf(monday.trim()));
        dates.add(mondayDate);
        dates.add(mondayDate.plusDays(1));
        dates.add(mondayDate.plusDays(2));
        dates.add(mondayDate.plusDays(3));
        dates.add(mondayDate.plusDays(4));
        dates.add(mondayDate.plusDays(5));
        dates.add(mondayDate.plusDays(6));
        return dates;
    }
}
