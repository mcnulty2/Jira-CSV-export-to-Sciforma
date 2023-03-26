package com.github.mcnulty2.timesaver.containers;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class HoursContainer {

    private HoursContainer() {
        // No public Constructor
    }

    public static void logHoursForProject(String project, List<String> weeklyTimes) {
        SelenideElement day = $(By.xpath("//div[contains(text(), '" + project + "')]"))
                .ancestor("tr").sibling(0).find(By.tagName("td")).sibling(1);
        for (String hoursForDay: weeklyTimes) {
            log.info(hoursForDay);
            day.sendKeys(hoursForDay);
            day = day.sibling(0);
        }
    }

}
