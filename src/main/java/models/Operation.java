package models;

import annotations.NotEmpty;
import annotations.NotNull;
import annotations.ValidDate;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
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

    @NotNull
    private OperationRoom operationRoom;

    @NotNull
    private Patient patient;

    @NotNull
    private Doctor doctor;

    @NotNull
    @NotEmpty
    @JsonDeserialize(as = ArrayList.class)
    private List<Nurse> nurses = new ArrayList<>();

    public Operation() {}
    public Operation(Timestamp startTime) {
        this.startTime = startTime;
        this.status = Status.Preparation;
    }

    public Operation.Status getStatus() { return status; }
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }
    public OperationRoom getOperationRoom() { return operationRoom; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public List<Nurse> getNurses() { return nurses; }

    public void setStatus(Operation.Status status) { this.status = status; }
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }
    public void setOperationRoom(OperationRoom operationRoom) { this.operationRoom = operationRoom; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public void setDoctor(Doctor doctor) {
        if (doctor == null) throw new IllegalArgumentException("doctor cannot be null");
        this.doctor = doctor;
    }

    public void setNurses(List<Nurse> nurses) {
        if (nurses == null || nurses.isEmpty())
            throw new IllegalArgumentException("nurses cannot be empty");
        this.nurses = new ArrayList<>(nurses);
    }

    /**
     *
     * @return time duration in minutes
     */
    public long calculateDuration() {
        long diffMillis = endTime.getTime() - startTime.getTime();
        return diffMillis / 60000;
    }
}
