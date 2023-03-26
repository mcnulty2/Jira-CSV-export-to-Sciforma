package com.github.mcnulty2.timesaver.javascript;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavascriptHelper {
    private WebDriver driver = null;

    public JavascriptHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void click(WebElement webElement) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", webElement);
    }

    public void setTextContent(WebElement webElement, String text) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].textContent='" + text + "';", webElement);
    }

    public void setAttribute(WebElement webElement, String name, String text) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute('" + name + "', '" + text + "');", webElement);
    }
}
