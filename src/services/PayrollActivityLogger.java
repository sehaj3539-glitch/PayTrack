package services;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

// PayrollActivityLogger.java - Thread-safe activity logger demonstrating LinkedList
public class PayrollActivityLogger {

    private final LinkedList<String> activityLog;

    public PayrollActivityLogger() {
        this.activityLog = new LinkedList<>();
    }

    public synchronized void log(String activity) {
        activityLog.add("[" + LocalDate.now() + "] " + activity);
        if (activityLog.size() > 10) {
            activityLog.removeFirst();
        }
    }

    public synchronized List<String> getRecentActivities() {
        return new LinkedList<>(activityLog);
    }
}
