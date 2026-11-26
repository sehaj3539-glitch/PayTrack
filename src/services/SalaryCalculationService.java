package services;

import java.util.Scanner;

import exceptions.ArithmeticExceptionHandler;
import exceptions.EmployeeNotFoundException;
import models.Employee;
import models.Salary;
import repositories.AttendanceRepository;
import repositories.SalaryRepository;

// SalaryCalculationService.java - Dedicated service for salary calculations and multithreading demo
public class SalaryCalculationService {

    private final AttendanceRepository attRepo;
    private final SalaryRepository salRepo;
    private final EmployeeSearchService searchService;

    public SalaryCalculationService(AttendanceRepository attRepo, SalaryRepository salRepo, EmployeeSearchService searchService) {
        this.attRepo = attRepo;
        this.salRepo = salRepo;
        this.searchService = searchService;
    }

    public void calculateSalary(Scanner scanner) {
        System.out.println("\n----------------------------------------------------");
        System.out.println("                 CALCULATE SALARY                   ");
        System.out.println("----------------------------------------------------");

        try {
            System.out.print("Enter Employee ID: ");
            int empId = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = searchService.searchEmployeeById(empId);

            System.out.println("Calculating Salary for: " + emp.getName() + " | Department: " + emp.getDepartment());
            System.out.printf("Monthly Basic Salary: Rs. %.2f\n", emp.getBasicSalary());

            int totalWorkingDays = 0;
            while (true) {
                System.out.print("Enter Total Working Days in Month (e.g. 30): ");
                try {
                    totalWorkingDays = Integer.parseInt(scanner.nextLine().trim());
                    ArithmeticExceptionHandler.validateWorkingDays(totalWorkingDays);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("[Error] Please enter a valid number for working days.");
                } catch (ArithmeticExceptionHandler e) {
                    System.out.println("[Error] Total working days must be greater than zero! Please enter again.");
                }
            }

            int loggedDays = attRepo.getPresentDaysCount(empId);
            System.out.println("Logged Present Days in Attendance Table: " + loggedDays + " Days");

            int presentDays = 0;
            while (true) {
                System.out.print("Enter Present Days (Press Enter to use logged count of " + loggedDays + "): ");
                String input = scanner.nextLine().trim();
                try {
                    if (input.isEmpty()) {
                        presentDays = loggedDays;
                    } else {
                        presentDays = Integer.parseInt(input);
                    }

                    ArithmeticExceptionHandler.validatePresentDays(presentDays, totalWorkingDays);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("[Error] Please enter a valid number for present days.");
                } catch (ArithmeticExceptionHandler e) {
                    System.out.println("[Error] " + e.getMessage() + " Please enter again.");
                }
            }

            // Multithreading demonstration: background thread simulating calculation
            System.out.print("[Processing] Calculating salary");
            Thread workerThread = new Thread(() -> {
                try {
                    for (int i = 0; i < 3; i++) {
                        Thread.sleep(150);
                        System.out.print(".");
                    }
                } catch (InterruptedException e) {
                    System.out.println("\nThread interrupted: " + e.getMessage());
                }
            });

            workerThread.start();
            workerThread.join();

            Salary sal = new Salary(empId, emp.getBasicSalary(), totalWorkingDays, presentDays);
            boolean ok = salRepo.save(sal);
            if (ok) {
                System.out.println("\n\n----------------------------------------------------");
                System.out.println("             SALARY COMPUTATION RESULT              ");
                System.out.println("----------------------------------------------------");
                System.out.printf(" Monthly Basic Salary : Rs. %.2f\n", sal.getBasicSalary());
                System.out.printf(" Total Working Days   : %d Days\n", sal.getTotalWorkingDays());
                System.out.printf(" Present Days         : %d Days\n", sal.getPresentDays());
                System.out.printf(" Daily Salary         : Rs. %.2f\n", sal.getDailySalary());
                System.out.println("----------------------------------------------------");
                System.out.printf(" FINAL SALARY         : Rs. %.2f\n", sal.getFinalSalary());
                System.out.println("----------------------------------------------------");
                System.out.println("[Success] Salary details saved to database successfully!");
            } else {
                System.out.println("\n[Failed] Could not save salary calculation to database.");
            }

        } catch (NumberFormatException e) {
            System.out.println("[Input Error] Invalid Employee ID format.");
        } catch (InterruptedException e) {
            System.out.println("[Thread Error] Calculation thread error: " + e.getMessage());
        } catch (EmployeeNotFoundException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }
}
