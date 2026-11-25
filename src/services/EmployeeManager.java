package services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import exceptions.EmployeeNotFoundException;
import models.Employee;
import repositories.DatabaseOperations;

// EmployeeManager.java - Coordinates Employee & Department management via specialized services
public class EmployeeManager {

    private final DatabaseOperations dbOps;
    private final Map<Integer, Employee> employeeCache;

    // Dedicated Sub-Services
    private final EmployeeCreationService creationService;
    private final EmployeeSearchService searchService;
    private final EmployeeUpdateService updateService;
    private final EmployeeDeletionService deletionService;
    private final DepartmentService departmentService;

    // 1D Array demonstrating One-Dimensional Array concept
    public static final String[] DEFAULT_DEPARTMENTS = {
        "Information Technology", "Human Resources", "Finance", "Marketing", "Operations"
    };

    // 2D Array demonstrating Two-Dimensional Array concept (Work Shifts)
    public static final String[][] WORK_SHIFTS = {
        {"General Shift", "09:00 AM - 05:00 PM"},
        {"Morning Shift", "06:00 AM - 02:00 PM"},
        {"Evening Shift", "02:00 PM - 10:00 PM"}
    };

    public EmployeeManager(DatabaseOperations dbOps) {
        this.dbOps = dbOps;
        this.employeeCache = new HashMap<>();

        // Initialize modular sub-services
        this.searchService = new EmployeeSearchService(dbOps, employeeCache);
        this.creationService = new EmployeeCreationService(dbOps, employeeCache);
        this.updateService = new EmployeeUpdateService(dbOps, employeeCache, searchService);
        this.deletionService = new EmployeeDeletionService(dbOps, employeeCache, searchService);
        this.departmentService = new DepartmentService(dbOps);
    }

    // 1. ADD EMPLOYEE
    public void addEmployee(Scanner scanner) {
        creationService.addEmployee(scanner);
    }

    // 2. VIEW ALL EMPLOYEES (Demonstrates Iterator and HashSet)
    public void viewEmployees() {
        System.out.println("\n--------------------------------------------------------------------------------------------------");
        System.out.println("                                      ALL EMPLOYEES LIST                                          ");
        System.out.println("--------------------------------------------------------------------------------------------------");

        List<Employee> list = dbOps.getAllEmployees();

        if (list.isEmpty()) {
            System.out.println("No employees found in the database.");
            return;
        }

        System.out.printf("| %-6s | %-20s | %-4s | %-6s | %-22s | %-12s | %-12s |\n",
                "EMP ID", "NAME", "AGE", "GENDER", "DEPARTMENT", "PHONE", "BASIC SALARY");
        System.out.println("--------------------------------------------------------------------------------------------------");

        // Iterator pattern demonstration
        Iterator<Employee> it = list.iterator();
        while (it.hasNext()) {
            Employee emp = it.next();
            emp.displayInfo(); // Polymorphic method call
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println("Total Employees: " + list.size());

        // HashSet demonstration for unique departments
        Set<String> uniqueDepts = new HashSet<>();
        for (Employee e : list) {
            uniqueDepts.add(e.getDepartment());
        }
        System.out.println("Active Departments (" + uniqueDepts.size() + "): " + uniqueDepts);
    }

    // 3. SEARCH EMPLOYEE
    public Employee searchEmployeeById(int empId) throws EmployeeNotFoundException {
        return searchService.searchEmployeeById(empId);
    }

    public void searchEmployee(Scanner scanner) {
        searchService.searchEmployee(scanner);
    }

    // 4. UPDATE EMPLOYEE
    public void updateEmployee(Scanner scanner) {
        updateService.updateEmployee(scanner);
    }

    // 5. DELETE EMPLOYEE
    public void deleteEmployee(Scanner scanner) {
        deletionService.deleteEmployee(scanner);
    }

    // 6. DEPARTMENT MANAGEMENT
    public void addDepartment(Scanner scanner) {
        departmentService.addDepartment(scanner);
    }

    public void viewDepartments() {
        departmentService.viewDepartments();
    }

    // 7. DEMONSTRATION OF GENERICS, LAMBDA, AND STREAMS
    public static <T> void printGenericList(List<T> list) {
        int index = 1;
        for (T item : list) {
            System.out.println((index++) + ". " + item);
        }
    }

    public void demonstrateStreamsAndLambda() {
        List<Employee> list = dbOps.getAllEmployees();
        if (list.isEmpty()) return;

        System.out.println("\n[Streams Demo 1: Filter] Employees with Basic Salary > 40000:");
        list.stream()
            .filter(e -> e.getBasicSalary() > 40000.0)
            .forEach(e -> System.out.println(" - " + e.getName() + " (Rs. " + e.getBasicSalary() + ")"));

        System.out.println("\n[Streams Demo 2: Map] List of Employee Names:");
        List<String> names = list.stream()
                                 .map(Employee::getName)
                                 .collect(Collectors.toList());
        System.out.println(" " + names);

        System.out.println("\n[Comparator Demo] Sorted by Name (Alphabetical):");
        List<Employee> sortedByName = new ArrayList<>(list);
        sortedByName.sort(Comparator.comparing(Employee::getName));
        for (Employee e : sortedByName) {
            System.out.println(" - " + e.getName() + " [" + e.getDepartment() + "]");
        }
    }

    // Sub-service accessors
    public DatabaseOperations getDbOps() { return dbOps; }
    public Map<Integer, Employee> getEmployeeCache() { return employeeCache; }
    public EmployeeCreationService getCreationService() { return creationService; }
    public EmployeeSearchService getSearchService() { return searchService; }
    public EmployeeUpdateService getUpdateService() { return updateService; }
    public EmployeeDeletionService getDeletionService() { return deletionService; }
    public DepartmentService getDepartmentService() { return departmentService; }
}
