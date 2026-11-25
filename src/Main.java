// Main.java - Main Application Entry Point with Console Menu
import java.util.Scanner;
import repositories.DatabaseOperations;
import services.EmployeeManager;
import services.PayrollManager;

public class Main {

    public static void main(String[] args) {
        // Initialize Core Components
        DatabaseOperations dbOps = new DatabaseOperations();
        EmployeeManager empManager = new EmployeeManager(dbOps);
        PayrollManager payrollManager = new PayrollManager(dbOps, empManager);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("==========================================================");
        System.out.println("   Welcome to PayTrack - Payroll & Salary Management      ");
        System.out.println("==========================================================");

        while (running) {
            printMenu();
            System.out.print("Enter your choice (1-11): ");
            String input = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[Invalid Input] Please enter a number between 1 and 11.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    empManager.addEmployee(scanner);
                    break;
                case 2:
                    empManager.viewEmployees();
                    break;
                case 3:
                    empManager.searchEmployee(scanner);
                    break;
                case 4:
                    empManager.updateEmployee(scanner);
                    break;
                case 5:
                    empManager.deleteEmployee(scanner);
                    break;
                case 6:
                    empManager.addDepartment(scanner);
                    break;
                case 7:
                    payrollManager.markAttendance(scanner);
                    break;
                case 8:
                    payrollManager.viewAttendance(scanner);
                    break;
                case 9:
                    payrollManager.calculateSalary(scanner);
                    break;
                case 10:
                    payrollManager.generatePayslip(scanner);
                    break;
                case 11:
                    System.out.println("\n==========================================================");
                    System.out.println("   Thank you for using PayTrack! Have a great day.        ");
                    System.out.println("==========================================================");
                    running = false;
                    break;
                case 99:
                    // Helper option to test Lambda and Streams for evaluation
                    empManager.demonstrateStreamsAndLambda();
                    break;
                default:
                    System.out.println("[Warning] Invalid choice (" + choice + "). Please choose from 1 to 11.");
            }

            if (running) {
                System.out.println("\nPress ENTER to continue to Main Menu...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    // Displays the exact required console menu
    private static void printMenu() {
        System.out.println();
        System.out.println("====================================");
        System.out.println("             PAYTRACK               ");
        System.out.println("====================================");
        System.out.println("1. Add Employee");
        System.out.println("2. View Employees");
        System.out.println("3. Search Employee");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Add Department");
        System.out.println("7. Mark Attendance");
        System.out.println("8. View Attendance");
        System.out.println("9. Calculate Salary");
        System.out.println("10. Generate Payslip");
        System.out.println("11. Exit");
        System.out.println("====================================");
    }
}
