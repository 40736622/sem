package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void printSalariesTestNull()
    {
        app.printSalaries(null);
    }

    @Test
    void printSalariesTestEmpty()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        app.printSalaries(employees);
    }

    @Test
    void printSalariesTestContainsNull()
    {
        ArrayList<Employee> employess = new ArrayList<Employee>();
        employess.add(null);
        app.printSalaries(employess);
    }

    @Test
    void printSalaries()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;
        employees.add(emp);
        app.printSalaries(employees);
    }

    @Test
    void displayEmployeeNull() {
        app.displayEmployee(null);
    }

    @Test
    void displayEmployeeTestEmpty()
    {
        Employee emp = new Employee();
        app.displayEmployee(emp);
    }

    @Test
    void displayEmployee()
    {
        Employee emp = new Employee();
        Department dept = new Department();
        Employee manager = new Employee();

        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;

        dept.dept_no = "d005";
        dept.dept_name = "Engineer";
        emp.dept = dept;

        manager.first_name = "John";
        manager.last_name = "Smith";
        emp.manager = manager;

        app.displayEmployee(emp);
    }
}