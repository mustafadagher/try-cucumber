package io.cucumber.rest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.Properties;

/**
 * Created by mustafadagher on 15/06/2017.
 */
public class BaseSeleniumSteps {
    private static final String WEB_DRIVER_FOLDER = "/usr/local/Cellar/chromedriver/2.30/bin/";

    protected WebDriver driver;

    protected void startWebDriver() {
        Properties props = System.getProperties();
        props.setProperty("webdriver.gecko.driver", WEB_DRIVER_FOLDER + "chromedriver");
        driver = new ChromeDriver();
        driver.navigate().to("http://en.wikipedia.org");
    }

    protected void stopWebDriver() {
        driver.quit();
    }

    protected void wait(int timeOutInSeconds) {
        try {
            Thread.sleep(timeOutInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String driversFolder(String path) {
        File file = new File(path);
        for (String item : file.list()) {
            if (WEB_DRIVER_FOLDER.equals(item)) {
                return file.getAbsolutePath() + "/" + WEB_DRIVER_FOLDER + "/";
            }
        }
        return driversFolder(file.getParent());
    }
}
