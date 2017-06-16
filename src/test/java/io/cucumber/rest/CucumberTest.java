package io.cucumber.rest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by mustafadagher on 13/06/2017.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:feature",
        format = {
                "json:target/cucumber/wikipedia.json",
                "html:target/cucumber/wikipedia.html",
                "pretty"
        })
public class CucumberTest {
}
