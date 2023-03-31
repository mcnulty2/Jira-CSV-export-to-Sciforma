package com.github.mcnulty2.timesaver.containers;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class WeekContainer {

    private WeekContainer() {
        // No public Constructor
    }

    public static void selectWeek(String weekConfigString) throws InterruptedException {
        Integer numberWeeks = Integer.valueOf(weekConfigString.substring(1));
        if (weekConfigString.contains("-")) {
            clickLeft(numberWeeks);
        } else if (weekConfigString.contains("+")) {
            clickRight(numberWeeks);
        }
    }

    private static void clickLeft(Integer numberWeeks) throws InterruptedException {
        SelenideElement buttonLeft = $$(".htmlDistributionToolbarButtonArrows").get(0).find("div");
        for (int i=0; i<numberWeeks; i++) {
            buttonLeft.click();
            Thread.sleep(5000);
        }
    }

    private static void clickRight(Integer numberWeeks) throws InterruptedException {
        SelenideElement buttonRight = $$(".htmlDistributionToolbarButtonArrows").get(5).find("div");
        for (int i=0; i<numberWeeks; i++) {
            buttonRight.click();
            Thread.sleep(5000);
        }
    }
}
