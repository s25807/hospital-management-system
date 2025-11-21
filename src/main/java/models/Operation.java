package models;

import annotations.NotNull;
import annotations.ValidDate;

import java.sql.Timestamp;

public class Operation {
    public enum Status { Preparation, Rescheduled, Ongoing, Cancelled, Completed };

    @NotNull
    @ValidDate(value = ValidDate.Mode.FUTURE)
    private Timestamp startTime;

    @NotNull
    @ValidDate(value = ValidDate.Mode.FUTURE)
    private Timestamp endTime;

    @NotNull
    @ValidDate(value = ValidDate.Mode.FUTURE)
    private Operation.Status status;

    public Operation() {}
    public Operation(Timestamp startTime) {
        this.startTime = startTime;
        this.status = Status.Preparation;
    }

    public Operation.Status getStatus() { return status; }
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }

    public void setStatus(Operation.Status status) { this.status = status; }
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
