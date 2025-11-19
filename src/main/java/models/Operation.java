package models;

import java.sql.Timestamp;

public class Operation {
    public enum Status { Preparation, Rescheduled, Ongoing, Cancelled, Completed };

    private Timestamp startTime;
    private Timestamp endTime;
    private Appointment.Status status;

    public Operation(Timestamp startTime) {
        this.startTime = startTime;
        this.status = Appointment.Status.Initialized;
    }

    public Appointment.Status getStatus() { return status; }
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }

    public void setStatus(Appointment.Status status) { this.status = status; }
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }

    /**
     *
     * @return time duration in minutes
     */
    public long calculateDuration() {
        long diffMillis = endTime.getTime() - startTime.getTime();
        return diffMillis / 60000;
    }
}
