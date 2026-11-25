package services;

import java.util.Scanner;

import repositories.DatabaseOperations;

// PayrollManager.java - Coordinates Attendance, Salary Calculations, and Payslip services
public class PayrollManager {

    // Dedicated Sub-Services
    private final PayrollActivityLogger activityLogger;
    private final AttendanceService attendanceService;
    private final SalaryCalculationService salaryCalculationService;
    private final PayslipService payslipService;

    public PayrollManager(DatabaseOperations dbOps, EmployeeManager empManager) {
        this.activityLogger = new PayrollActivityLogger();
        this.attendanceService = new AttendanceService(dbOps, empManager.getSearchService(), activityLogger);
        this.salaryCalculationService = new SalaryCalculationService(dbOps, empManager.getSearchService(), activityLogger);
        this.payslipService = new PayslipService(dbOps, empManager.getSearchService());
    }

    // Thread-safe activity logging delegation
    public synchronized void logPayrollActivity(String activity) {
        activityLogger.log(activity);
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

    // Sub-service accessors
    public PayrollActivityLogger getActivityLogger() { return activityLogger; }
    public AttendanceService getAttendanceService() { return attendanceService; }
    public SalaryCalculationService getSalaryCalculationService() { return salaryCalculationService; }
    public PayslipService getPayslipService() { return payslipService; }
}
