package utils;

// PayslipFormatter.java - Builds formatted text representation of payslips using StringBuilder
public class PayslipFormatter {

    public static String formatPayslip(int empId, String empName, String dept,
                                       double basicSalary, int totalWorkingDays,
                                       int presentDays, double dailySalary, double finalSalary) {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================================\n");
        sb.append("                 PAYSLIP SUMMARY                    \n");
        sb.append("               PAYTRACK SOLUTIONS                   \n");
        sb.append("====================================================\n");
        sb.append(String.format(" Employee ID          : %d\n", empId));
        sb.append(String.format(" Employee Name        : %s\n", empName));
        sb.append(String.format(" Department           : %s\n", dept));
        sb.append("----------------------------------------------------\n");
        sb.append(String.format(" Monthly Basic Salary : Rs. %.2f\n", basicSalary));
        sb.append(String.format(" Total Working Days   : %d Days\n", totalWorkingDays));
        sb.append(String.format(" Present Days         : %d Days\n", presentDays));
        sb.append(String.format(" Daily Salary         : Rs. %.2f\n", dailySalary));
        sb.append("----------------------------------------------------\n");
        sb.append(String.format(" FINAL NET SALARY     : Rs. %.2f\n", finalSalary));
        sb.append("====================================================\n");
        return sb.toString();
    }
}
