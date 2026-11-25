package repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import models.Employee;

// EmployeeRepository.java - Dedicated file persistence for Employee entities
public class EmployeeRepository {

    private static final String DATA_DIR = "data";
    private static final String EMPLOYEES_FILE = DATA_DIR + File.separator + "employees.txt";

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        initStorage();
    }

    private synchronized void initStorage() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        loadEmployees();
    }

    public synchronized int getNextSequentialEmpId() {
        return employees.stream().mapToInt(Employee::getEmpId).max().orElse(0) + 1;
    }

    public synchronized int insert(Employee emp) {
        int assignedId = getNextSequentialEmpId();
        emp.setEmpId(assignedId);
        employees.add(emp);
        saveEmployees();
        return assignedId;
    }

    public synchronized List<Employee> getAll() {
        return employees.stream()
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .collect(Collectors.toList());
    }

    public synchronized Employee getById(int empId) {
        for (Employee emp : employees) {
            if (emp.getEmpId() == empId) {
                return emp;
            }
        }
        return null;
    }

    public synchronized boolean update(Employee emp) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmpId() == emp.getEmpId()) {
                employees.set(i, emp);
                saveEmployees();
                return true;
            }
        }
        return false;
    }

    public synchronized boolean delete(int empId) {
        boolean removed = employees.removeIf(e -> e.getEmpId() == empId);
        if (removed) {
            saveEmployees();
        }
        return removed;
    }

    public synchronized void saveEmployees() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(EMPLOYEES_FILE))) {
            for (Employee e : employees) {
                bw.write(e.getEmpId() + "|" + e.getName() + "|" + e.getAge() + "|" +
                         e.getGender() + "|" + e.getDepartment() + "|" + e.getPhone() + "|" +
                         e.getBasicSalary());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[File Storage Error] Saving employees: " + e.getMessage());
        }
    }

    public synchronized void loadEmployees() {
        employees.clear();
        File file = new File(EMPLOYEES_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    String gender = parts[3].trim();
                    String dept = parts[4].trim();
                    String phone = parts[5].trim();
                    double salary = Double.parseDouble(parts[6].trim());
                    employees.add(new Employee(id, name, age, gender, dept, phone, salary));
                }
            }
        } catch (Exception e) {
            System.err.println("[File Storage Error] Loading employees: " + e.getMessage());
        }
    }
}
