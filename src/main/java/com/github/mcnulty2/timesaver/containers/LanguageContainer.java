package com.github.mcnulty2.timesaver.containers;

import com.codeborne.selenide.SelenideElement;
import com.github.mcnulty2.timesaver.data.EnumTranslations;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LanguageContainer {

    private LanguageContainer() {
        // No public Constructor
    }

    public static String detectLocale() throws InterruptedException {
        String locale = "en_us";
        SelenideElement german = $(By.partialLinkText(EnumTranslations.TIMESHEET.getText("de")));
        SelenideElement french = $(By.partialLinkText(EnumTranslations.TIMESHEET.getText("fr")));
        if (german.exists()) {
            locale = "de_ch";
        } else if (french.exists()) {
            locale = "fr_ch";
        }
        return locale;
    }
}
