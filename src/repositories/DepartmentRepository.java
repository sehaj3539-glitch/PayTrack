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

import models.Department;

// DepartmentRepository.java - Dedicated file persistence for Department entities
public final class DepartmentRepository {

    private static final String DATA_DIR = "data";
    private static final String DEPARTMENTS_FILE = DATA_DIR + File.separator + "departments.txt";

    private final List<Department> departments = new ArrayList<>();

    public DepartmentRepository() {
        initStorage();
    }

    private synchronized void initStorage() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        loadDepartments();
    }

    public synchronized int getNextSequentialDeptId() {
        return departments.stream().mapToInt(Department::getDeptId).max().orElse(0) + 1;
    }

    public synchronized boolean insert(Department dept) {
        for (Department d : departments) {
            if (d.getDeptName().equalsIgnoreCase(dept.getDeptName().trim())) {
                System.err.println("[Database Error] Insert department failed (may already exist): Duplicate department name");
                return false;
            }
        }
        int assignedId = getNextSequentialDeptId();
        dept.setDeptId(assignedId);
        departments.add(dept);
        saveDepartments();
        return true;
    }

    public synchronized List<Department> getAll() {
        return departments.stream()
                .sorted(Comparator.comparingInt(Department::getDeptId))
                .collect(Collectors.toList());
    }

    public synchronized void saveDepartments() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DEPARTMENTS_FILE))) {
            for (Department d : departments) {
                bw.write(d.getDeptId() + "|" + d.getDeptName());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[File Storage Error] Saving departments: " + e.getMessage());
        }
    }

    public synchronized void loadDepartments() {
        departments.clear();
        File file = new File(DEPARTMENTS_FILE);
        if (!file.exists() || file.length() == 0) {
            seedInitialDepartments();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    departments.add(new Department(id, name));
                }
            }
        } catch (Exception e) {
            System.err.println("[File Storage Error] Loading departments: " + e.getMessage());
        }
    }

    private void seedInitialDepartments() {
        insert(new Department("Information Technology"));
        insert(new Department("Human Resources"));
        insert(new Department("Finance"));
        insert(new Department("Marketing"));
    }
}
