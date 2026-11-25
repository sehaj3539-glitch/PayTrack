package models;

// Employee.java - Subclass demonstrating Inheritance, Polymorphism, and Comparable interface
public class Employee extends Person implements Comparable<Employee> {

    // Static constant (Demonstrates static keyword)
    public static final String COMPANY_NAME = "PayTrack Solutions";

    // Instance variables specific to Employee
    private int empId;
    private String department;
    private double basicSalary;

    // Default constructor
    public Employee() {
        super();
    }

    // Parameterized constructor using 'super' and 'this'
    public Employee(int empId, String name, int age, String gender, String department, String phone, double basicSalary) {
        super(name, age, gender, phone); // Calls Person constructor
        this.empId = empId;
        this.department = department;
        this.basicSalary = basicSalary;
    }

    // Constructor without empId (used when adding new employee before DB generates ID)
    public Employee(String name, int age, String gender, String department, String phone, double basicSalary) {
        super(name, age, gender, phone);
        this.empId = 0;
        this.department = department;
        this.basicSalary = basicSalary;
    }

    // Getters and Setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    // Polymorphism: Overriding abstract method from Person class
    @Override
    public void displayInfo() {
        System.out.printf("| %-6d | %-20s | %-4d | %-6s | %-22s | %-12s | %-12.2f |\n",
                empId, name, age, gender, department, phone, basicSalary);
    }

    // Natural sorting by Employee ID using Comparable interface
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.empId, other.empId);
    }

    // Overriding toString() for easy debugging and printing
    @Override
    public String toString() {
        return "Employee [ID=" + empId + ", Name=" + name + ", Age=" + age + 
               ", Gender=" + gender + ", Department=" + department + 
               ", Phone=" + phone + ", BasicSalary=" + basicSalary + "]";
    }
}
