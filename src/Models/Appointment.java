package Models;

import java.security.Timestamp;

public class Appointment {
    public enum Status { Initialized, Scheduled, InProgress, Cancelled, Completed };

    private Timestamp startTime;
    private Timestamp endTime;
    private Status status;

    public Appointment(Timestamp startTime) {
        this.startTime = startTime;
        this.status = Status.Initialized;
    }

    public Status getStatus() { return status; }
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }

    public void setStatus(Status status) { this.status = status; }
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }

    public long calculateDuration() {
        long diffMillis = endTime.getTimestamp().getTime() - startTime.getTimestamp().getTime();
        return diffMillis / 60000;
    }
}
