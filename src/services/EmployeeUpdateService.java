package services;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import exceptions.EmployeeNotFoundException;
import models.Department;
import models.Employee;
import repositories.DepartmentRepository;
import repositories.EmployeeRepository;
import utils.InputValidator;
import validators.PhoneValidator;

// EmployeeUpdateService.java - Dedicated service for updating existing employee records
public class EmployeeUpdateService {

    private final EmployeeRepository empRepo;
    private final DepartmentRepository deptRepo;
    private final Map<Integer, Employee> employeeCache;
    private final EmployeeSearchService searchService;

    public EmployeeUpdateService(EmployeeRepository empRepo, DepartmentRepository deptRepo, Map<Integer, Employee> employeeCache, EmployeeSearchService searchService) {
        this.empRepo = empRepo;
        this.deptRepo = deptRepo;
        this.employeeCache = employeeCache;
        this.searchService = searchService;
    }

    public void updateEmployee(Scanner scanner) {
        System.out.print("\nEnter Employee ID to update: ");
        try {
            int empId = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = searchService.searchEmployeeById(empId);

            System.out.println("\nCurrent Details: " + emp.getName() + " | Dept: " + emp.getDepartment() + " | Basic: Rs." + emp.getBasicSalary());
            System.out.println("(Press ENTER without typing to keep the current value)\n");

            System.out.print("Enter New Name [" + emp.getName() + "]: ");
            String nameInput = scanner.nextLine().trim();
            if (!nameInput.isEmpty()) emp.setName(InputValidator.toTitleCase(nameInput));

            System.out.print("Enter New Age [" + emp.getAge() + "]: ");
            String ageStr = scanner.nextLine().trim();
            if (!ageStr.isEmpty()) emp.setAge(Integer.parseInt(ageStr));

            System.out.print("Enter New Gender [" + emp.getGender() + "]: ");
            String genderInput = scanner.nextLine().trim();
            if (!genderInput.isEmpty()) {
                String formatted = InputValidator.formatGender(genderInput);
                if (formatted != null) {
                    emp.setGender(formatted);
                } else {
                    System.out.println("[Warning] Invalid gender! Keeping current value: " + emp.getGender());
                }
            }

            System.out.print("Do you want to change Department? (yes/no): ");
            String changeDept = scanner.nextLine().trim();
            if (changeDept.equalsIgnoreCase("yes") || changeDept.equalsIgnoreCase("y")) {
                List<Department> depts = deptRepo.getAll();
                System.out.println("Select New Department:");
                for (int i = 0; i < depts.size(); i++) {
                    System.out.println((i + 1) + ". " + depts.get(i).getDeptName());
                }
                System.out.print("Enter Department Number (1-" + depts.size() + "): ");
                try {
                    int dNum = Integer.parseInt(scanner.nextLine().trim());
                    if (dNum >= 1 && dNum <= depts.size()) {
                        emp.setDepartment(depts.get(dNum - 1).getDeptName());
                        System.out.println("Department updated to: " + emp.getDepartment());
                    } else {
                        System.out.println("[Warning] Invalid choice! Keeping current department: " + emp.getDepartment());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[Warning] Invalid input! Keeping current department.");
                }
            }

            System.out.print("Enter New Phone [" + emp.getPhone() + "]: ");
            String phoneInput = scanner.nextLine().trim();
            if (!phoneInput.isEmpty()) {
                if (PhoneValidator.isValid(phoneInput)) {
                    emp.setPhone(phoneInput);
                } else {
                    System.out.println("[Warning] Invalid phone number! Must be 10 digits. Keeping current: " + emp.getPhone());
                }
            }

            System.out.print("Enter New Basic Salary [" + emp.getBasicSalary() + "]: ");
            String salaryStr = scanner.nextLine().trim();
            if (!salaryStr.isEmpty()) emp.setBasicSalary(Double.parseDouble(salaryStr));

            boolean ok = empRepo.update(emp);
            if (ok) {
                employeeCache.put(emp.getEmpId(), emp);
                System.out.println("\n[Success] Employee ID " + empId + " updated successfully!");
            } else {
                System.out.println("\n[Failed] Could not update employee record.");
            }

        } catch (NumberFormatException e) {
            System.out.println("[Input Error] Invalid number format.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }
}
