package services;

import java.util.Map;
import java.util.Scanner;

import exceptions.EmployeeNotFoundException;
import models.Employee;
import repositories.DatabaseOperations;

// EmployeeDeletionService.java - Dedicated service for deleting employees and cache invalidation
public class EmployeeDeletionService {

    private final DatabaseOperations dbOps;
    private final Map<Integer, Employee> employeeCache;
    private final EmployeeSearchService searchService;

    public EmployeeDeletionService(DatabaseOperations dbOps, Map<Integer, Employee> employeeCache, EmployeeSearchService searchService) {
        this.dbOps = dbOps;
        this.employeeCache = employeeCache;
        this.searchService = searchService;
    }

    public void deleteEmployee(Scanner scanner) {
        System.out.print("\nEnter Employee ID to delete: ");
        try {
            int empId = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = searchService.searchEmployeeById(empId);

            System.out.print("Are you sure you want to delete '" + emp.getName() + "' (ID: " + empId + ")? (yes/no): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
                boolean ok = dbOps.deleteEmployee(empId);
                if (ok) {
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
}
