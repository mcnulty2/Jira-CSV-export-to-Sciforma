package com.github.mcnulty2.timesaver.containers;

import com.codeborne.selenide.Selectors;
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
        SelenideElement german = $(Selectors.withText(EnumTranslations.WELCOME.getText("de")));
        SelenideElement french = $(Selectors.withText(EnumTranslations.WELCOME.getText("de")));
        if (german.exists()) {
            locale = "de_ch";
        } else if (french.exists()) {
            locale = "fr_ch";
        }
        return locale;
    }
}
