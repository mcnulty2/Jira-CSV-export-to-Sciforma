package com.github.mcnulty2.timesaver.containers;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginContainer {

    private LoginContainer() {
        // No public Constructor
    }

    public static void login(String user, String password) {
        $(By.id("login/loginId")).sendKeys(user);
        $(By.id("login/password")).sendKeys(password);
        $(By.id("login/button")).click();
    }
}
