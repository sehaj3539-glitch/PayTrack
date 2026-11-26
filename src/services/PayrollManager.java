package services;

import java.util.Scanner;

import repositories.AttendanceRepository;
import repositories.SalaryRepository;

// PayrollManager.java - Coordinates Attendance, Salary Calculations, and Payslip services
public class PayrollManager {

    // Dedicated Sub-Services
    private final AttendanceService attendanceService;
    private final SalaryCalculationService salaryCalculationService;
    private final PayslipService payslipService;

    public PayrollManager(EmployeeManager empManager) {
        AttendanceRepository attRepo = new AttendanceRepository();
        SalaryRepository salRepo = new SalaryRepository();
        this.attendanceService = new AttendanceService(attRepo, empManager.getSearchService());
        this.salaryCalculationService = new SalaryCalculationService(attRepo, salRepo, empManager.getSearchService());
        this.payslipService = new PayslipService(salRepo, empManager.getSearchService());
    }

    // 1. ATTENDANCE MANAGEMENT
    public void markAttendance(Scanner scanner) {
        attendanceService.markAttendance(scanner);
    }

    public void viewAttendance(Scanner scanner) {
        attendanceService.viewAttendance(scanner);
    }

    // 2. SALARY CALCULATION
    public void calculateSalary(Scanner scanner) {
        salaryCalculationService.calculateSalary(scanner);
    }

    // 3. GENERATE PAYSLIP
    public void generatePayslip(Scanner scanner) {
        payslipService.generatePayslip(scanner);
    }
}
