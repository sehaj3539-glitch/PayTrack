package services;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import exceptions.EmployeeNotFoundException;
import models.Attendance;
import models.Employee;
import repositories.DatabaseOperations;

// AttendanceService.java - Dedicated service for tracking and displaying employee attendance
public class AttendanceService {

    private final DatabaseOperations dbOps;
    private final EmployeeSearchService searchService;
    private final PayrollActivityLogger logger;

    public AttendanceService(DatabaseOperations dbOps, EmployeeSearchService searchService, PayrollActivityLogger logger) {
        this.dbOps = dbOps;
        this.searchService = searchService;
        this.logger = logger;
    }

    public void markAttendance(Scanner scanner) {
        System.out.println("\n----------------------------------------------------");
        System.out.println("                 MARK ATTENDANCE                    ");
        System.out.println("----------------------------------------------------");

        try {
            System.out.print("Enter Employee ID: ");
            int empId = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = searchService.searchEmployeeById(empId);

            System.out.println("Marking attendance for: " + emp.getName() + " (" + emp.getDepartment() + ")");

            String today = LocalDate.now().toString();
            System.out.print("Enter Attendance Date (YYYY-MM-DD) [Default: " + today + "]: ");
            String dateInput = scanner.nextLine().trim();
            String date = dateInput.isEmpty() ? today : dateInput;

            System.out.print("Enter Status (P for Present / A for Absent): ");
            String statusInput = scanner.nextLine().trim();
            String status;

            if (statusInput.equalsIgnoreCase("P") || statusInput.equalsIgnoreCase("Present")) {
                status = "Present";
            } else if (statusInput.equalsIgnoreCase("A") || statusInput.equalsIgnoreCase("Absent")) {
                status = "Absent";
            } else {
                System.out.println("[Warning] Invalid status! Please enter 'Present' or 'Absent'.");
                return;
            }

            Attendance att = new Attendance(empId, date, status);
            boolean ok = dbOps.markAttendance(att);
            if (ok) {
                logger.log("Marked " + status + " for Emp ID: " + empId + " on " + date);
                System.out.println("\n[Success] Attendance marked as '" + status + "' for Employee ID " + empId + " on " + date);
            } else {
                System.out.println("[Failed] Could not save attendance record.");
            }

        } catch (NumberFormatException e) {
            System.out.println("[Error] Please enter a valid numeric Employee ID.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    public void viewAttendance(Scanner scanner) {
        System.out.print("\nEnter Employee ID to view attendance: ");
        try {
            int empId = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = searchService.searchEmployeeById(empId);

            List<Attendance> records = dbOps.getAttendanceByEmpId(empId);
            int presentCount = dbOps.getPresentDaysCount(empId);

            System.out.println("\n----------------------------------------------------");
            System.out.println("          ATTENDANCE RECORD: " + emp.getName());
            System.out.println("----------------------------------------------------");

            if (records.isEmpty()) {
                System.out.println("No attendance records logged for this employee yet.");
            } else {
                System.out.printf("| %-8s | %-15s | %-10s |\n", "ATT ID", "DATE", "STATUS");
                System.out.println("----------------------------------------------------");
                for (Attendance a : records) {
                    System.out.printf("| %-8d | %-15s | %-10s |\n", a.getAttId(), a.getAttDate(), a.getStatus());
                }
                System.out.println("----------------------------------------------------");
            }

            System.out.println(">> Total Days Present: " + presentCount + " Days");
            System.out.println("----------------------------------------------------");

        } catch (NumberFormatException e) {
            System.out.println("[Error] Please enter a valid numeric Employee ID.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }
}
