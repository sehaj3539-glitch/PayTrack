package repositories;

import java.util.List;

import interfaces.DataRepository;
import models.Attendance;
import models.Department;
import models.Employee;
import models.Salary;

// DatabaseOperations.java - Repository Facade delegating to dedicated entity repositories
public class DatabaseOperations implements DataRepository {

    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;
    private final AttendanceRepository attendanceRepo;
    private final SalaryRepository salaryRepo;

    public DatabaseOperations() {
        this.employeeRepo = new EmployeeRepository();
        this.departmentRepo = new DepartmentRepository();
        this.attendanceRepo = new AttendanceRepository();
        this.salaryRepo = new SalaryRepository();

        initSeedIfNeeded();
    }

    // Seed initial demo data if all repositories are currently empty
    private void initSeedIfNeeded() {
        if (employeeRepo.getAll().isEmpty() && departmentRepo.getAll().isEmpty()) {
            departmentRepo.insert(new Department("Information Technology"));
            departmentRepo.insert(new Department("Human Resources"));
            departmentRepo.insert(new Department("Finance"));
            departmentRepo.insert(new Department("Marketing"));

            employeeRepo.insert(new Employee("Rahul Sharma", 28, "Male", "Information Technology", "9876543210", 45000.00));
            employeeRepo.insert(new Employee("Priya Patel", 26, "Female", "Human Resources", "9876543211", 38000.00));
            employeeRepo.insert(new Employee("Amit Verma", 32, "Male", "Finance", "9876543212", 52000.00));
            employeeRepo.insert(new Employee("Sneha Rao", 25, "Female", "Marketing", "9876543213", 35000.00));

            attendanceRepo.mark(new Attendance(1, "2026-11-01", "Present"));
            attendanceRepo.mark(new Attendance(1, "2026-11-02", "Present"));
            attendanceRepo.mark(new Attendance(1, "2026-11-03", "Absent"));
            attendanceRepo.mark(new Attendance(1, "2026-11-04", "Present"));
            attendanceRepo.mark(new Attendance(2, "2026-11-01", "Present"));
            attendanceRepo.mark(new Attendance(2, "2026-11-02", "Present"));
            attendanceRepo.mark(new Attendance(3, "2026-11-01", "Present"));

            salaryRepo.save(new Salary(1, 45000.00, 30, 3));
            salaryRepo.save(new Salary(2, 38000.00, 30, 2));
        }
    }

    // 1. Employee CRUD Operations (Delegated to EmployeeRepository)
    @Override
    public synchronized int insertEmployee(Employee emp) {
        return employeeRepo.insert(emp);
    }

    @Override
    public synchronized List<Employee> getAllEmployees() {
        return employeeRepo.getAll();
    }

    @Override
    public synchronized Employee getEmployeeById(int empId) {
        return employeeRepo.getById(empId);
    }

    @Override
    public synchronized boolean updateEmployee(Employee emp) {
        return employeeRepo.update(emp);
    }

    @Override
    public synchronized boolean deleteEmployee(int empId) {
        boolean deleted = employeeRepo.delete(empId);
        if (deleted) {
            attendanceRepo.deleteByEmpId(empId);
            salaryRepo.deleteByEmpId(empId);
        }
        return deleted;
    }

    // 2. Department Operations (Delegated to DepartmentRepository)
    @Override
    public synchronized boolean insertDepartment(Department dept) {
        return departmentRepo.insert(dept);
    }

    @Override
    public synchronized List<Department> getAllDepartments() {
        return departmentRepo.getAll();
    }

    // 3. Attendance Operations (Delegated to AttendanceRepository)
    @Override
    public synchronized boolean markAttendance(Attendance att) {
        return attendanceRepo.mark(att);
    }

    @Override
    public synchronized List<Attendance> getAttendanceByEmpId(int empId) {
        return attendanceRepo.getByEmpId(empId);
    }

    @Override
    public synchronized int getPresentDaysCount(int empId) {
        return attendanceRepo.getPresentDaysCount(empId);
    }

    // 4. Salary Operations (Delegated to SalaryRepository)
    @Override
    public synchronized boolean saveSalary(Salary sal) {
        return salaryRepo.save(sal);
    }

    @Override
    public synchronized Salary getSalaryByEmpId(int empId) {
        return salaryRepo.getByEmpId(empId);
    }

    // Direct repository getters for modular access
    public EmployeeRepository getEmployeeRepository() {
        return employeeRepo;
    }

    public DepartmentRepository getDepartmentRepository() {
        return departmentRepo;
    }

    public AttendanceRepository getAttendanceRepository() {
        return attendanceRepo;
    }

    public SalaryRepository getSalaryRepository() {
        return salaryRepo;
    }
}
