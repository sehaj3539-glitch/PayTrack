package services;

import java.util.List;
import java.util.Scanner;

import models.Department;
import repositories.DatabaseOperations;

// DepartmentService.java - Dedicated service for department management and work shifts display
public class DepartmentService {

    private final DatabaseOperations dbOps;

    public DepartmentService(DatabaseOperations dbOps) {
        this.dbOps = dbOps;
    }

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
        boolean ok = dbOps.insertDepartment(dept);
        if (ok) {
            System.out.println("[Success] Department '" + deptName + "' added successfully (ID: " + dept.getDeptId() + ").");
        } else {
            System.out.println("[Failed] Could not add department. It might already exist.");
        }
    }

    public void viewDepartments() {
        System.out.println("\n----------------------------------------------------");
        System.out.println("                 DEPARTMENTS LIST                   ");
        System.out.println("----------------------------------------------------");
        List<Department> list = dbOps.getAllDepartments();

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
        for (int i = 0; i < EmployeeManager.WORK_SHIFTS.length; i++) {
            System.out.println(" - " + EmployeeManager.WORK_SHIFTS[i][0] + " : " + EmployeeManager.WORK_SHIFTS[i][1]);
        }
    }
}
