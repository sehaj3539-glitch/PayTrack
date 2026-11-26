package services;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import exceptions.EmployeeNotFoundException;
import models.Attendance;
import models.Employee;
import repositories.AttendanceRepository;

// AttendanceService.java - Dedicated service for tracking and displaying employee attendance
public class AttendanceService {

    private final AttendanceRepository attRepo;
    private final EmployeeSearchService searchService;

    public AttendanceService(AttendanceRepository attRepo, EmployeeSearchService searchService) {
        this.attRepo = attRepo;
        this.searchService = searchService;
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
            boolean ok = attRepo.mark(att);
            if (ok) {
                System.out.println("\n[Success] Attendance marked as '" + status + "' for Employee ID " + empId + " on " + date);
            } else {
                System.out.println("\n[Failed] Could not record attendance.");
            }

        } catch (NumberFormatException e) {
            System.out.println("[Error] Please enter a valid numeric Employee ID.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    public void viewAttendance(Scanner scanner) {
        System.out.println("\n----------------------------------------------------");
        System.out.println("                 VIEW ATTENDANCE                    ");
        System.out.println("----------------------------------------------------");

        try {
            System.out.print("Enter Employee ID to view attendance: ");
            int empId = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = searchService.searchEmployeeById(empId);

            List<Attendance> list = attRepo.getByEmpId(empId);
            int presentCount = attRepo.getPresentDaysCount(empId);

            System.out.println("\nAttendance Records for: " + emp.getName() + " (ID: " + empId + ", Dept: " + emp.getDepartment() + ")");
            System.out.println("----------------------------------------------------");

            if (list.isEmpty()) {
                System.out.println("No attendance records found for Employee ID: " + empId);
            } else {
                System.out.printf("| %-8s | %-12s | %-10s |\n", "ATT ID", "DATE", "STATUS");
                System.out.println("----------------------------------------------------");
                for (Attendance a : list) {
                    System.out.printf("| %-8d | %-12s | %-10s |\n", a.getAttId(), a.getAttDate(), a.getStatus());
                }
                System.out.println("----------------------------------------------------");
                System.out.println("Total Records: " + list.size() + " | Present Days: " + presentCount + " | Absent Days: " + (list.size() - presentCount));
            }

        } catch (NumberFormatException e) {
            System.out.println("[Error] Please enter a valid numeric Employee ID.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }
}
