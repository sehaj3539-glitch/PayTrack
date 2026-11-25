package interfaces;

import java.util.List;
import models.Employee;
import models.Department;
import models.Attendance;
import models.Salary;

// DataRepository.java - Interface defining data persistence contract for PayTrack
public interface DataRepository {

    // Employee operations
    int insertEmployee(Employee emp);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int empId);
    boolean updateEmployee(Employee emp);
    boolean deleteEmployee(int empId);

    // Department operations
    boolean insertDepartment(Department dept);
    List<Department> getAllDepartments();

    // Attendance operations
    boolean markAttendance(Attendance att);
    List<Attendance> getAttendanceByEmpId(int empId);
    int getPresentDaysCount(int empId);

    // Salary operations
    boolean saveSalary(Salary sal);
    Salary getSalaryByEmpId(int empId);
}
