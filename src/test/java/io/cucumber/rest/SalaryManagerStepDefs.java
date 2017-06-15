package io.cucumber.rest;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import io.cucumber.rest.model.Employee;
import io.cucumber.rest.service.SalaryManager;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;

import java.util.List;

/**
 * Created by mustafadagher on 15/06/2017.
 */
public class SalaryManagerStepDefs implements En {

    SalaryManager manager;

    public SalaryManagerStepDefs() {
        Given("^the salary management system is initialized with the following data$", (DataTable employees) -> {
            manager = new SalaryManager(employees.asList(Employee.class));
        });

        When("^the boss increases the salary for the employee with id '(\\d+)' by (\\d+)%$", (Integer id, Integer increaseInPercent) -> {
            manager.increaseSalary(id, increaseInPercent);
        });

        Then("^the payroll for the employee with id '(\\d+)' should display a salary of (\\d+)$", (Integer id, Float salary) -> {
            Employee nominee = manager.getPayroll(id);
            MatcherAssert.assertThat(nominee.getSalary(), IsEqual.equalTo(salary));
        });
    }


}
