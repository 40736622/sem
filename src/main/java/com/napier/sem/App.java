package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/employees?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public Employee getEmployeeReduced(int ID) {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT emp_no, first_name, last_name "
                                + "FROM employees "
                                + "WHERE emp_no = " + ID;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Return new employee if valid
            if (rset.next()) {
                Employee emp = new Employee();

                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");

                return emp;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public Employee getEmployee(int ID) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT e.emp_no, e.first_name, e.last_name, t.title, s.salary, d.dept_name, d.dept_no, "
                            + "mgr.emp_no AS manager_emp_no, mgr.first_name AS manager_fname, mgr.last_name AS manager_lname "
                            + "FROM employees e "
                            + "JOIN titles t ON e.emp_no = t.emp_no "
                            + "JOIN salaries s ON e.emp_no = s.emp_no "
                            + "JOIN dept_emp de ON e.emp_no = de.emp_no "
                            + "JOIN departments d ON de.dept_no = d.dept_no "
                            + "JOIN dept_manager dm ON d.dept_no = dm.dept_no "
                            + "JOIN employees mgr ON dm.emp_no = mgr.emp_no "
                            + "WHERE e.emp_no = " + ID + " "
                            + "AND t.to_date = '9999-01-01' AND dm.to_date = '9999-01-01' AND s.to_date = '9999-01-01'";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Return new employee if valid
            if (rset.next()) {
                Employee emp = new Employee();
                Employee mngr = new Employee();
                Department dept = new Department();

                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");

                dept.dept_no = rset.getString("dept_no");
                dept.dept_name = rset.getString("dept_name");
                emp.dept = dept;

                mngr.emp_no = rset.getInt("manager_emp_no");
                mngr.first_name = rset.getString("manager_fname");
                mngr.last_name = rset.getString("manager_lname");
                emp.manager = mngr;

                return emp;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public Employee getEmployeeByName(String first_name, String last_name) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT e.emp_no, e.first_name, e.last_name, t.title, s.salary, d.dept_name, d.dept_no, "
                            + "mgr.emp_no AS manager_emp_no, mgr.first_name AS manager_fname, mgr.last_name AS manager_lname "
                            + "FROM employees e "
                            + "JOIN titles t ON e.emp_no = t.emp_no "
                            + "JOIN salaries s ON e.emp_no = s.emp_no "
                            + "JOIN dept_emp de ON e.emp_no = de.emp_no "
                            + "JOIN departments d ON de.dept_no = d.dept_no "
                            + "JOIN dept_manager dm ON d.dept_no = dm.dept_no "
                            + "JOIN employees mgr ON dm.emp_no = mgr.emp_no "
                            + "WHERE e.first_name = '" + first_name + "' AND e.last_name = '" + last_name + "' "
                            + "AND t.to_date = '9999-01-01' AND dm.to_date = '9999-01-01' AND s.to_date = '9999-01-01'";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Return new employee if valid
            if (rset.next()) {
                Employee emp = new Employee();
                Employee mngr = new Employee();
                Department dept = new Department();

                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");

                dept.dept_no = rset.getString("dept_no");
                dept.dept_name = rset.getString("dept_name");
                emp.dept = dept;

                mngr.emp_no = rset.getInt("manager_emp_no");
                mngr.first_name = rset.getString("manager_fname");
                mngr.last_name = rset.getString("manager_lname");
                emp.manager = mngr;

                return emp;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }


    public void displayEmployee(Employee emp) {
        if (emp != null) {
            System.out.println(
                    emp.emp_no + " "
                            + (emp.first_name != null ? emp.first_name : "N/A") + " "
                            + (emp.last_name != null ? emp.last_name : "N/A") + "\n"
                            + (emp.title != null ? emp.title : "N/A") + "\n"
                            + "Salary: " + emp.salary + "\n"
                            + (emp.dept != null && emp.dept.dept_name != null ? emp.dept.dept_name : "No Department") + "\n"
                            + "Manager: "
                            + (emp.manager != null && emp.manager.first_name != null ? emp.manager.first_name : "N/A") + " "
                            + (emp.manager != null && emp.manager.last_name != null ? emp.manager.last_name : "N/A") + "\n"
            );
        } else {
            System.out.println("No employee");
        }
    }

    /**
     * Gets all the current employees and salaries.
     *
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalaries() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                            + "ORDER BY employees.emp_no ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Gets all the current employees and salaries by role.
     *
     * @return A list of all employees and salaries by role, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalariesByRole(String role) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries, titles "
                            + "WHERE employees.emp_no = salaries.emp_no "
                            + "AND employees.emp_no = titles.emp_no "
                            + "AND salaries.to_date = '9999-01-01' "
                            + "AND titles.to_date = '9999-01-01' "
                            + "AND titles.title = '" + role + "' "
                            + "ORDER BY employees.emp_no ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Prints a list of employees.
     *
     * @param employees The list of employees to print.
     */
    public void printSalaries(ArrayList<Employee> employees)
    {
        // Check employees is not null
        if (employees == null)
        {
            System.out.println("No employees");
            return;
        }
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employees)
        {
            if (emp == null)
                continue;
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }

    public Department getDepartment(String dept_name) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT d.dept_no, d.dept_name, mgr.emp_no, mgr.first_name, mgr.last_name "
                            + "FROM departments d, dept_manager dm, employees mgr "
                            + "WHERE d.dept_no = dm.dept_no "
                            + "AND dm.emp_no = mgr.emp_no "
                            + "AND dm.to_date = '9999-01-01' "
                            + "AND d.dept_name = '" + dept_name + "'";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Return new department if valid
            if (rset.next()) {

                Department dept = new Department();
                Employee mngr = new Employee();

                dept.dept_no = rset.getString("d.dept_no");
                dept.dept_name = rset.getString("d.dept_name");

                mngr.emp_no = rset.getInt("mgr.emp_no");
                mngr.first_name = rset.getString("mgr.first_name");
                mngr.last_name = rset.getString("mgr.last_name");
                dept.manager = mngr;

                return dept;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get department details");
            return null;
        }
    }

    public ArrayList<Employee> getSalariesByDepartment(Department dept) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries, dept_emp, departments "
                            + "WHERE employees.emp_no = salaries.emp_no "
                            + "AND employees.emp_no = dept_emp.emp_no "
                            + "AND dept_emp.dept_no = departments.dept_no "
                            + "AND salaries.to_date = '9999-01-01'  "
                            + "AND departments.dept_no = '" + dept.dept_no + "' "
                            + "ORDER BY employees.emp_no ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    public void addEmployee(Employee emp)
    {
        try
        {
            Statement stmt = con.createStatement();
            String strUpdate =
                    "INSERT INTO employees (emp_no, first_name, last_name, birth_date, gender, hire_date) " +
                            "VALUES (" + emp.emp_no + ", '" + emp.first_name + "', '" + emp.last_name + "', " +
                            "'9999-01-01', 'M', '9999-01-01')";
            stmt.execute(strUpdate);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to add employee");
        }
    }

    public ArrayList<Employee> getSalariesByRole(String role) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, titles.title, "
                            + "salaries.salary, departments.dept_name, dept_manager.emp_no "
                            + "FROM employees, salaries, titles, departments, dept_emp, dept_manager "
                            + "WHERE employees.emp_no = salaries.emp_no "
                            + "AND salaries.to_date = '9999-01-01' "
                            + "AND titles.emp_no = employees.emp_no "
                            + "AND titles.to_date = '9999-01-01' "
                            + "AND dept_emp.emp_no = employees.emp_no "
                            + "AND dept_emp.to_date = '9999-01-01' "
                            + "AND departments.dept_no = dept_emp.dept_no "
                            + "AND dept_manager.dept_no = dept_emp.dept_no "
                            + "AND dept_manager.to_date = '9999-01-01' "
                            + "AND titles.title = '" + role + "'";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<>();
            while (rset.next()) {
                Employee emp = new Employee();
                Department dept = new Department();
                Employee manager = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.salary = rset.getInt("salary");
                emp.title = rset.getString("title");

                dept.dept_name = rset.getString("departments.dept_name");
                emp.dept = dept;

                manager.emp_no = rset.getInt("dept_manager.emp_no");
                emp.manager = manager;

                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }


    /**
     * Outputs to Markdown
     *
     * @param employees
     */
    public void outputEmployees(ArrayList<Employee> employees, String filename) {
        // Check employees is not null
        if (employees == null) {
            System.out.println("No employees");
            return;
        }

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("| Emp No | First Name | Last Name | Title | Salary | Department |                    Manager |\r\n");
        sb.append("| --- | --- | --- | --- | --- | --- | --- |\r\n");
        // Loop over all employees in the list
        for (Employee emp : employees) {
            if (emp == null) continue;
            sb.append("| " + emp.emp_no + " | " +
                    emp.first_name + " | " + emp.last_name + " | " +
                    emp.title + " | " + emp.salary + " | "
                    + emp.dept.dept_name + " | " + emp.manager.emp_no + " |\r\n");
        }
        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create new Application and connect to database
        App app = new App();

        if (args.length < 1) {
            app.connect("localhost:33060", 0);
        } else {
            app.connect(args[0], Integer.parseInt(args[1]));
        }

        ArrayList<Employee> employees = app.getSalariesByRole("Manager");
        app.outputEmployees(employees, "ManagerSalaries.md");

        // Disconnect from database
        app.disconnect();
    }
}