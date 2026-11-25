package services;

import java.util.Scanner;

import exceptions.EmployeeNotFoundException;
import models.Employee;
import models.Salary;
import repositories.DatabaseOperations;

// PayslipService.java - Dedicated service for payslip retrieval and console presentation
public class PayslipService {

    private final DatabaseOperations dbOps;
    private final EmployeeSearchService searchService;

    public PayslipService(DatabaseOperations dbOps, EmployeeSearchService searchService) {
        this.dbOps = dbOps;
        this.searchService = searchService;
    }

    public void generatePayslip(Scanner scanner) {
        System.out.println("\n----------------------------------------------------");
        System.out.println("                 GENERATE PAYSLIP                   ");
        System.out.println("----------------------------------------------------");

        try {
            System.out.print("Enter Employee ID: ");
            int empId = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = searchService.searchEmployeeById(empId);

            Salary sal = dbOps.getSalaryByEmpId(empId);
            if (sal == null) {
                System.out.println("[Warning] Salary has not been calculated for this employee yet!");
                System.out.println("Please run Option 9 (Calculate Salary) first.");
                return;
            }

            String payslipText = sal.buildPayslipText(emp.getName(), emp.getDepartment());
            System.out.println("\n" + payslipText);

        } catch (NumberFormatException e) {
            System.out.println("[Error] Please enter a valid numeric Employee ID.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }
}
