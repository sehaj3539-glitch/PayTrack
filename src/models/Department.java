package models;

// Department.java - Department entity representing organizational departments
public class Department {
    private int deptId;
    private String deptName;

    // Default constructor
    public Department() {
    }

    // Parameterized constructor
    public Department(int deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    // Constructor without deptId (used before DB insertion)
    public Department(String deptName) {
        this.deptId = 0;
        this.deptName = deptName;
    }

    // Getters and Setters
    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Department [ID=" + deptId + ", Name=" + deptName + "]";
    }
}
