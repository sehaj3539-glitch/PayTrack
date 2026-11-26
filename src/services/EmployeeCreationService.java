package services;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import models.Department;
import models.Employee;
import repositories.DepartmentRepository;
import repositories.EmployeeRepository;
import utils.InputValidator;
import validators.PhoneValidator;

// EmployeeCreationService.java - Dedicated service for gathering input and registering employees
public class EmployeeCreationService {

    private final EmployeeRepository empRepo;
    private final DepartmentRepository deptRepo;
    private final Map<Integer, Employee> employeeCache;

    public EmployeeCreationService(EmployeeRepository empRepo, DepartmentRepository deptRepo, Map<Integer, Employee> employeeCache) {
        this.empRepo = empRepo;
        this.deptRepo = deptRepo;
        this.employeeCache = employeeCache;
    }

    public void addEmployee(Scanner scanner) {
        System.out.println("\n----------------------------------------------------");
        System.out.println("                 ADD NEW EMPLOYEE                   ");
        System.out.println("----------------------------------------------------");

        try {
            System.out.print("Enter Employee Name: ");
            String nameInput = scanner.nextLine().trim();
            if (nameInput.isEmpty()) {
                System.out.println("[Warning] Name cannot be empty.");
                return;
            }
            String name = InputValidator.toTitleCase(nameInput);

            System.out.print("Enter Age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            if (age < 18 || age > 70) {
                System.out.println("[Warning] Age must be between 18 and 70.");
                return;
            }

            String gender = "";
            while (true) {
                System.out.print("Enter Gender (Male/Female/Other): ");
                String genderInput = scanner.nextLine().trim();
                String formatted = InputValidator.formatGender(genderInput);
                if (formatted != null) {
                    gender = formatted;
                    break;
                } else {
                    System.out.println("[Error] Invalid gender! Please enter Male, Female, or Other.");
                }
            }

            List<Department> depts = deptRepo.getAll();
            if (depts.isEmpty()) {
                for (String dName : EmployeeManager.DEFAULT_DEPARTMENTS) {
                    deptRepo.insert(new Department(dName));
                }
                depts = deptRepo.getAll();
            }

            System.out.println("\nSelect Department:");
            for (int i = 0; i < depts.size(); i++) {
                System.out.println((i + 1) + ". " + depts.get(i).getDeptName());
            }

            String department = "";
            while (true) {
                System.out.print("Enter Department Number (1-" + depts.size() + "): ");
                String input = scanner.nextLine().trim();
                try {
                    int deptChoice = Integer.parseInt(input);
                    if (deptChoice >= 1 && deptChoice <= depts.size()) {
                        department = depts.get(deptChoice - 1).getDeptName();
                        System.out.println("Assigned Department: " + department);
                        break;
                    } else {
                        System.out.println("[Error] Invalid department number! Please choose between 1 and " + depts.size() + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[Error] Invalid input! Please enter a valid number.");
                }
            }

            String phone = "";
            while (true) {
                System.out.print("Enter 10-Digit Phone Number: ");
                phone = scanner.nextLine().trim();
                if (PhoneValidator.isValid(phone)) {
                    break;
                } else {
                    System.out.println("[Error] Invalid phone number! Must contain exactly 10 digits (no letters or special characters).");
                }
            }

            System.out.print("Enter Monthly Basic Salary (Rs.): ");
            double basicSalary = Double.parseDouble(scanner.nextLine().trim());
            if (basicSalary < 0) {
                System.out.println("[Warning] Basic salary cannot be negative.");
                return;
            }

            Employee emp = new Employee(name, age, gender, department, phone, basicSalary);
            int generatedId = empRepo.insert(emp);
            if (generatedId > 0) {
                employeeCache.put(generatedId, emp);
                System.out.println("\n[Success] Employee added successfully! Automatically Assigned ID: " + generatedId);
            } else {
                System.out.println("[Failed] Could not add employee to database.");
            }

        } catch (NumberFormatException e) {
            System.out.println("[Input Error] Invalid number entered! Please enter valid numeric values for age/salary.");
        }
    }
}
