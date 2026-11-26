package services;

import java.util.Map;
import java.util.Scanner;

import exceptions.EmployeeNotFoundException;
import models.Employee;
import repositories.EmployeeRepository;

// EmployeeSearchService.java - Dedicated service for employee lookups and in-memory cache
public class EmployeeSearchService {

    private final EmployeeRepository empRepo;
    private final Map<Integer, Employee> employeeCache;

    public EmployeeSearchService(EmployeeRepository empRepo, Map<Integer, Employee> employeeCache) {
        this.empRepo = empRepo;
        this.employeeCache = employeeCache;
    }

    public Employee searchEmployeeById(int empId) throws EmployeeNotFoundException {
        if (employeeCache.containsKey(empId)) {
            return employeeCache.get(empId);
        }

        Employee emp = empRepo.getById(empId);
        if (emp == null) {
            throw new EmployeeNotFoundException("Employee with ID " + empId + " was not found in the database!");
        }

        employeeCache.put(empId, emp);
        return emp;
    }

    public void searchEmployee(Scanner scanner) {
        System.out.print("\nEnter Employee ID to search: ");
        try {
            int empId = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = searchEmployeeById(empId);

            System.out.println("\n----------------------------------------------------");
            System.out.println("                 EMPLOYEE DETAILS                   ");
            System.out.println("----------------------------------------------------");
            System.out.println("Employee ID   : " + emp.getEmpId());
            System.out.println("Name          : " + emp.getName());
            System.out.println("Age           : " + emp.getAge());
            System.out.println("Gender        : " + emp.getGender());
            System.out.println("Department    : " + emp.getDepartment());
            System.out.println("Phone Number  : " + emp.getPhone());
            System.out.println("Basic Salary  : Rs. " + emp.getBasicSalary());
            System.out.println("Company       : " + Employee.COMPANY_NAME);
            System.out.println("----------------------------------------------------");

        } catch (NumberFormatException e) {
            System.out.println("[Error] Please enter a valid numeric Employee ID.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("[Search Result] " + e.getMessage());
        }
    }
}
