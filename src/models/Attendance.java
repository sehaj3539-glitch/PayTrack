package models;

// Attendance.java - Attendance entity for tracking daily employee presence
public class Attendance {
    private int attId;
    private int empId;
    private String attDate;
    private String status; // "Present" or "Absent"

    // Default constructor
    public Attendance() {
    }

    // Parameterized constructor with ID
    public Attendance(int attId, int empId, String attDate, String status) {
        this.attId = attId;
        this.empId = empId;
        this.attDate = attDate;
        this.status = status;
    }

    // Constructor without attId (used when marking attendance)
    public Attendance(int empId, String attDate, String status) {
        this.attId = 0;
        this.empId = empId;
        this.attDate = attDate;
        this.status = status;
    }

    // Getters and Setters
    public int getAttId() {
        return attId;
    }

    public void setAttId(int attId) {
        this.attId = attId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getAttDate() {
        return attDate;
    }

    public void setAttDate(String attDate) {
        this.attDate = attDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance [ID=" + attId + ", EmpID=" + empId + ", Date=" + attDate + ", Status=" + status + "]";
    }
}
