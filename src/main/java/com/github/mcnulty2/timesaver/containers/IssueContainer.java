package com.github.mcnulty2.timesaver.containers;

import com.codeborne.selenide.SelenideElement;
import com.github.mcnulty2.timesaver.data.EnumTranslations;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDate;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class IssueContainer {

    private IssueContainer() {
        // No public Constructor
    }

    public static void logIssuesForProject(String project, List<String> weeklyIssues, List<LocalDate> weeklyDates, String language) {
        SelenideElement projectNotes = $(By.xpath("//div[contains(text(), '" + project + "')]"))
                .ancestor("tr").sibling(0).find(By.tagName("td")).sibling(11);
        projectNotes.click();
        SelenideElement dialogTitle = $(By.xpath("//div[contains(text(), '" + EnumTranslations.DAILY_NOTES.getText(language) + "')]"));
        SelenideElement topContainer = dialogTitle.ancestor("container").preceding(0);
        SelenideElement monday = topContainer.find(By.xpath(".//div[contains(text(), '" + weeklyDates.get(0).getDayOfMonth() + "')]"));
        SelenideElement notesTd = monday.ancestor("td").sibling(1);
        for (String issue: weeklyIssues) {
            Actions a = new Actions(notesTd.getWrappedDriver());
            a.sendKeys(notesTd, issue).build().perform();
            notesTd = notesTd.ancestor("tr").sibling(0).find("td").sibling(1);
        }
        SelenideElement popup = $(By.id("popup-container"));
        log.info(popup.innerText());
        popup.find(By.xpath(".//button[contains(text(), '" + EnumTranslations.CLOSE.getText(language) + "')]")).click();
    }
}
