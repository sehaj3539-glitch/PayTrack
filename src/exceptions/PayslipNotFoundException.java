package exceptions;

// PayslipNotFoundException.java - Custom Exception when payslip/salary record is missing
public class PayslipNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public PayslipNotFoundException() {
        super("No calculated payslip found for this employee!");
    }

    public PayslipNotFoundException(String message) {
        super(message);
    }
}
