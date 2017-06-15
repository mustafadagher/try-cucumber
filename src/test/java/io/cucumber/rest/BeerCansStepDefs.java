package io.cucumber.rest;

import cucumber.api.java8.En;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;

/**
 * Created by mustafadagher on 15/06/2017.
 */
public class BeerCansStepDefs implements En {

    Integer balance = 0;

    public BeerCansStepDefs() {
        Given("^I have (\\d+) beer cans$", (Integer openingBalance) -> {
            balance = openingBalance;
        });

        And("^I have drunk (\\d+) beer cans$", (Integer processed) -> {
            balance -= processed;
        });

        When("^I go to my fridge$", () -> {
        });

        Then("^I should have (\\d+) beer cans$", (Integer inStock) -> {
            MatcherAssert.assertThat(balance, IsEqual.equalTo(inStock));
        });

    }

}
