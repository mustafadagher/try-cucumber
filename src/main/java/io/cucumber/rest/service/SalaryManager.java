package io.cucumber.rest.service;

import io.cucumber.rest.model.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by mustafadagher on 15/06/2017.
 */
public class SalaryManager {
    private Map<Integer, Employee> employees = new HashMap<>();

    public SalaryManager(final List<Employee> employees) {
        this.employees = employees.stream().collect(Collectors.toMap(Employee::getId, Function.<Employee> identity()));
    }

    public void increaseSalary(final Integer id, final int increaseInPercent) {
        Employee nominee = employees.get(id);
        float oldSalary = nominee.getSalary();
        nominee.setSalary(oldSalary + oldSalary * increaseInPercent / 100);
    }

    public Employee getPayroll(final int id) {
        return employees.get(id);
    }
}
