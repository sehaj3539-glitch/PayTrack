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

import models.Attendance;

// AttendanceRepository.java - Dedicated file persistence for Attendance entities
public class AttendanceRepository {

    private static final String DATA_DIR = "data";
    private static final String ATTENDANCE_FILE = DATA_DIR + File.separator + "attendance.txt";

    private final List<Attendance> attendanceList = new ArrayList<>();

    public AttendanceRepository() {
        initStorage();
    }

    private synchronized void initStorage() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        loadAttendance();
    }

    public synchronized int getNextSequentialAttId() {
        return attendanceList.stream().mapToInt(Attendance::getAttId).max().orElse(0) + 1;
    }

    public synchronized boolean mark(Attendance att) {
        int assignedId = getNextSequentialAttId();
        att.setAttId(assignedId);
        attendanceList.add(att);
        saveAttendance();
        return true;
    }

    public synchronized List<Attendance> getByEmpId(int empId) {
        return attendanceList.stream()
                .filter(a -> a.getEmpId() == empId)
                .sorted(Comparator.comparingInt(Attendance::getAttId))
                .collect(Collectors.toList());
    }

    public synchronized int getPresentDaysCount(int empId) {
        int count = 0;
        for (Attendance a : attendanceList) {
            if (a.getEmpId() == empId && "Present".equalsIgnoreCase(a.getStatus())) {
                count++;
            }
        }
        return count;
    }

    public synchronized void deleteByEmpId(int empId) {
        boolean removed = attendanceList.removeIf(a -> a.getEmpId() == empId);
        if (removed) {
            saveAttendance();
        }
    }

    public synchronized void saveAttendance() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ATTENDANCE_FILE))) {
            for (Attendance a : attendanceList) {
                bw.write(a.getAttId() + "|" + a.getEmpId() + "|" + a.getAttDate() + "|" + a.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[File Storage Error] Saving attendance: " + e.getMessage());
        }
    }

    public synchronized void loadAttendance() {
        attendanceList.clear();
        File file = new File(ATTENDANCE_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    int attId = Integer.parseInt(parts[0].trim());
                    int empId = Integer.parseInt(parts[1].trim());
                    String date = parts[2].trim();
                    String status = parts[3].trim();
                    attendanceList.add(new Attendance(attId, empId, date, status));
                }
            }
        } catch (Exception e) {
            System.err.println("[File Storage Error] Loading attendance: " + e.getMessage());
        }
    }
}
