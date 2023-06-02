package com.github.mcnulty2.timesaver.containers;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class HoursContainer {

    private HoursContainer() {
        // No public Constructor
    }

    public static void logHoursForProject(String project, List<String> weeklyTimes) throws InterruptedException {
        SelenideElement day = $(By.xpath("//div[contains(text(), '" + project + "')]"))
                .ancestor("tr").sibling(0).find(By.tagName("td")).sibling(1);
        while (!day.exists() || !day.isDisplayed()) {
            Thread.sleep(2000);
        }
        for (String hoursForDay: weeklyTimes) {
            if (!hoursForDay.replace(",", ".").equals("0.00")) {
                Actions a = new Actions(day.getWrappedDriver());
                int c = 0;
                while(c < 10 && (!day.find("input").exists() || !day.find("input").attr("value").equals(hoursForDay))) {
                    a.sendKeys(day, hoursForDay).build().perform();
                    c++;
                }
            }
            day = day.sibling(0);
        }
    }

}
