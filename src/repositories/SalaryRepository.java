package repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Salary;

// SalaryRepository.java - Dedicated file persistence for Salary records
public final class SalaryRepository {

    private static final String DATA_DIR = "data";
    private static final String SALARY_FILE = DATA_DIR + File.separator + "salary.txt";

    private final List<Salary> salaryList = new ArrayList<>();

    public SalaryRepository() {
        initStorage();
    }

    private synchronized void initStorage() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        loadSalaries();
    }

    public synchronized int getNextSequentialSalId() {
        return salaryList.stream().mapToInt(Salary::getSalId).max().orElse(0) + 1;
    }

    public synchronized boolean save(Salary sal) {
        for (int i = 0; i < salaryList.size(); i++) {
            if (salaryList.get(i).getEmpId() == sal.getEmpId()) {
                sal.setSalId(salaryList.get(i).getSalId());
                salaryList.set(i, sal);
                saveSalaryList();
                return true;
            }
        }
        int assignedId = getNextSequentialSalId();
        sal.setSalId(assignedId);
        salaryList.add(sal);
        saveSalaryList();
        return true;
    }

    public synchronized Salary getByEmpId(int empId) {
        for (Salary s : salaryList) {
            if (s.getEmpId() == empId) {
                return s;
            }
        }
        return null;
    }

    public synchronized void saveSalaryList() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SALARY_FILE))) {
            for (Salary s : salaryList) {
                bw.write(s.getSalId() + "|" + s.getEmpId() + "|" + s.getBasicSalary() + "|" +
                         s.getTotalWorkingDays() + "|" + s.getPresentDays() + "|" +
                         s.getDailySalary() + "|" + s.getFinalSalary());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[File Storage Error] Saving salaries: " + e.getMessage());
        }
    }

    public synchronized void loadSalaries() {
        salaryList.clear();
        File file = new File(SALARY_FILE);
        if (!file.exists() || file.length() == 0) {
            seedInitialSalaries();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    int salId = Integer.parseInt(parts[0].trim());
                    int empId = Integer.parseInt(parts[1].trim());
                    double basic = Double.parseDouble(parts[2].trim());
                    int totalDays = Integer.parseInt(parts[3].trim());
                    int present = Integer.parseInt(parts[4].trim());
                    double daily = Double.parseDouble(parts[5].trim());
                    double finalSal = Double.parseDouble(parts[6].trim());
                    salaryList.add(new Salary(salId, empId, basic, totalDays, present, daily, finalSal));
                }
            }
        } catch (Exception e) {
            System.err.println("[File Storage Error] Loading salaries: " + e.getMessage());
        }
    }

    private void seedInitialSalaries() {
        save(new Salary(1, 45000.00, 30, 3));
        save(new Salary(2, 38000.00, 30, 2));
    }
}
