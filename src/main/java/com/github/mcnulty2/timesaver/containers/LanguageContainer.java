package com.github.mcnulty2.timesaver.containers;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static com.codeborne.selenide.Selenide.$;

public class LanguageContainer {

    private LanguageContainer() {
        // No public Constructor
    }

    public static void setLanguage(String language) {
        Select select = new Select($(By.id("login/language")));
        select.selectByValue(language);
    }
}
