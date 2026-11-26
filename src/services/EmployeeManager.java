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
import models.Department;
import models.Employee;
import repositories.DepartmentRepository;
import repositories.EmployeeRepository;

// EmployeeManager.java - Coordinates Employee & Department management
public class EmployeeManager {

    private final EmployeeRepository empRepo;
    private final DepartmentRepository deptRepo;
    private final Map<Integer, Employee> employeeCache;

    // Dedicated Sub-Services
    private final EmployeeCreationService creationService;
    private final EmployeeSearchService searchService;
    private final EmployeeUpdateService updateService;

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

    public EmployeeManager() {
        this.empRepo = new EmployeeRepository();
        this.deptRepo = new DepartmentRepository();
        this.employeeCache = new HashMap<>();

        this.searchService = new EmployeeSearchService(empRepo, employeeCache);
        this.creationService = new EmployeeCreationService(empRepo, deptRepo, employeeCache);
        this.updateService = new EmployeeUpdateService(empRepo, deptRepo, employeeCache, searchService);
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

        List<Employee> list = empRepo.getAll();

        if (list.isEmpty()) {
            System.out.println("No employees found in the database.");
            return;
        }

        System.out.printf("| %-6s | %-20s | %-4s | %-6s | %-22s | %-12s | %-12s |\n",
                "EMP ID", "NAME", "AGE", "GENDER", "DEPARTMENT", "PHONE", "BASIC SALARY");
        System.out.println("--------------------------------------------------------------------------------------------------");

        Iterator<Employee> it = list.iterator();
        while (it.hasNext()) {
            Employee emp = it.next();
            emp.displayInfo();
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println("Total Employees: " + list.size());

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
        System.out.print("\nEnter Employee ID to delete: ");
        try {
            int empId = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = searchService.searchEmployeeById(empId);

            System.out.print("Are you sure you want to delete '" + emp.getName() + "' (ID: " + empId + ")? (yes/no): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
                if (empRepo.delete(empId)) {
                    employeeCache.remove(empId);
                    System.out.println("\n[Success] Employee ID " + empId + " deleted successfully!");
                } else {
                    System.out.println("\n[Failed] Could not delete employee from database.");
                }
            } else {
                System.out.println("Deletion cancelled by user.");
            }
        } catch (NumberFormatException e) {
            System.out.println("[Input Error] Please enter a valid numeric Employee ID.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    // 6. DEPARTMENT MANAGEMENT
    public void addDepartment(Scanner scanner) {
        System.out.println("\n----------------------------------------------------");
        System.out.println("                 ADD NEW DEPARTMENT                 ");
        System.out.println("----------------------------------------------------");
        System.out.print("Enter Department Name: ");
        String deptName = scanner.nextLine().trim();

        if (deptName.isEmpty()) {
            System.out.println("[Warning] Department name cannot be empty.");
            return;
        }

        Department dept = new Department(deptName);
        if (deptRepo.insert(dept)) {
            System.out.println("[Success] Department '" + deptName + "' added successfully (ID: " + dept.getDeptId() + ").");
        } else {
            System.out.println("[Failed] Could not add department. It might already exist.");
        }
    }

    public void viewDepartments() {
        System.out.println("\n----------------------------------------------------");
        System.out.println("                 DEPARTMENTS LIST                   ");
        System.out.println("----------------------------------------------------");
        List<Department> list = deptRepo.getAll();

        if (list.isEmpty()) {
            System.out.println("No departments recorded in database.");
            return;
        }

        System.out.printf("| %-8s | %-35s |\n", "DEPT ID", "DEPARTMENT NAME");
        System.out.println("----------------------------------------------------");
        for (Department d : list) {
            System.out.printf("| %-8d | %-35s |\n", d.getDeptId(), d.getDeptName());
        }
        System.out.println("----------------------------------------------------");

        System.out.println("\nStandard Work Shifts (2D Array Demonstration):");
        for (int i = 0; i < WORK_SHIFTS.length; i++) {
            System.out.println(" - " + WORK_SHIFTS[i][0] + " : " + WORK_SHIFTS[i][1]);
        }
    }

    // Streams and Lambda demonstration
    public void demonstrateStreamsAndLambda() {
        List<Employee> list = empRepo.getAll();
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

    public EmployeeSearchService getSearchService() { return searchService; }
}
