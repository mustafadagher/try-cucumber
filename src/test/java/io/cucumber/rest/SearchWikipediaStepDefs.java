package io.cucumber.rest;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by mustafadagher on 15/06/2017.
 */
public class SearchWikipediaStepDefs extends BaseSeleniumSteps implements En {

    public SearchWikipediaStepDefs() {
        Given("^Open http://en.wikipedia.org$", () -> {
            startWebDriver();
        });

        Given("^Enter search term '(.+)'$", (String searchTerm) -> {
            WebElement searchField = driver.findElement(By.id("searchInput"));
            searchField.sendKeys(searchTerm);
        });

        When("^Do search$", () -> {
            WebElement searchButton = driver.findElement(By.id("searchButton"));
            searchButton.click();
            wait(2);
        });

        Then("^Single result is shown for '(.*?)'$", (String searchResult) -> {
            WebElement results = driver.findElement(By.cssSelector("div#mw-content-text.mw-content-ltr p"));
            Assert.assertFalse(results.getText().contains(searchResult + " may refer to:"));
            Assert.assertTrue(results.getText().startsWith(searchResult));
            stopWebDriver();
        });
    }
}
