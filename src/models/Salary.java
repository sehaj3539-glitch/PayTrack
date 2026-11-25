package models;

// Salary.java - Salary calculation entity demonstrating StringBuilder and mathematical processing
public class Salary {
    private int salId;
    private int empId;
    private double basicSalary;       // Monthly Basic Salary
    private int totalWorkingDays;     // Total working days in month
    private int presentDays;          // Days employee was present
    private double dailySalary;       // Daily Salary = Monthly Basic / Total Working Days
    private double finalSalary;       // Final Salary = Daily Salary * Present Days

    // Backward compatibility fields
    private double allowances;
    private double deductions;
    private double grossSalary;
    private double netSalary;

    // Default constructor
    public Salary() {
    }

    // Constructor with new formula parameters
    public Salary(int empId, double basicSalary, int totalWorkingDays, int presentDays) {
        this.salId = 0;
        this.empId = empId;
        this.basicSalary = basicSalary;
        this.totalWorkingDays = totalWorkingDays;
        this.presentDays = presentDays;
        this.allowances = 0.0;
        this.deductions = 0.0;
        calculateSalary();
    }

    // Full constructor including ID and computed values
    public Salary(int salId, int empId, double basicSalary, int totalWorkingDays, int presentDays, double dailySalary, double finalSalary) {
        this.salId = salId;
        this.empId = empId;
        this.basicSalary = basicSalary;
        this.totalWorkingDays = totalWorkingDays;
        this.presentDays = presentDays;
        this.dailySalary = dailySalary;
        this.finalSalary = finalSalary;
        this.grossSalary = basicSalary;
        this.netSalary = finalSalary;
    }

    // Legacy constructor (keeps any existing code working)
    public Salary(int salId, int empId, double basicSalary, double allowances, double deductions, double grossSalary, double netSalary) {
        this.salId = salId;
        this.empId = empId;
        this.basicSalary = basicSalary;
        this.allowances = allowances;
        this.deductions = deductions;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
        this.totalWorkingDays = 30;
        this.presentDays = 30;
        this.dailySalary = basicSalary / 30.0;
        this.finalSalary = netSalary;
    }

    // Basic Salary Calculation Formula:
    // Daily Salary = Monthly Basic Salary / Total Working Days
    // Final Salary = Daily Salary * Present Days
    public void calculateSalary() {
        if (totalWorkingDays > 0) {
            this.dailySalary = this.basicSalary / this.totalWorkingDays;
            this.finalSalary = this.dailySalary * this.presentDays;
        } else {
            this.dailySalary = 0.0;
            this.finalSalary = 0.0;
        }
        this.grossSalary = this.basicSalary;
        this.netSalary = this.finalSalary;
    }

    // Getters and Setters
    public int getSalId() {
        return salId;
    }

    public void setSalId(int salId) {
        this.salId = salId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
        calculateSalary();
    }

    public int getTotalWorkingDays() {
        return totalWorkingDays;
    }

    public void setTotalWorkingDays(int totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
        calculateSalary();
    }

    public int getPresentDays() {
        return presentDays;
    }

    public void setPresentDays(int presentDays) {
        this.presentDays = presentDays;
        calculateSalary();
    }

    public double getDailySalary() {
        return dailySalary;
    }

    public void setDailySalary(double dailySalary) {
        this.dailySalary = dailySalary;
    }

    public double getFinalSalary() {
        return finalSalary;
    }

    public void setFinalSalary(double finalSalary) {
        this.finalSalary = finalSalary;
        this.netSalary = finalSalary;
    }

    public double getAllowances() {
        return allowances;
    }

    public void setAllowances(double allowances) {
        this.allowances = allowances;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
        this.finalSalary = netSalary;
    }

    // Demonstrates PayslipFormatter and StringBuilder for building string-heavy formatted payslips
    public String buildPayslipText(String empName, String dept) {
        return utils.PayslipFormatter.formatPayslip(
            empId, empName, dept, basicSalary, totalWorkingDays, presentDays, dailySalary, finalSalary
        );
    }

    public String buildPayslipText(String empName, String dept, int presentDays) {
        this.presentDays = presentDays;
        calculateSalary();
        return buildPayslipText(empName, dept);
    }

    @Override
    public String toString() {
        return "Salary [EmpID=" + empId + ", Basic=" + basicSalary + ", TotalDays=" + totalWorkingDays + 
               ", PresentDays=" + presentDays + ", DailySalary=" + dailySalary + ", FinalSalary=" + finalSalary + 
               ", NetSalary=" + netSalary + "]";
    }
}
