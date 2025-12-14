package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import annotations.NotNull;
import annotations.ValidDate;

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

    private OperationRoom operationRoom;
    private Doctor surgeon;
    @NotNull
    @JsonDeserialize(as = ArrayList.class)
    private List<Nurse> assistingNurses;
    private Patient patient;

    public Operation() {
        assistingNurses = new ArrayList<>();
    }
    public Operation(Timestamp startTime) {
        this.startTime = startTime;
        this.status = Status.Preparation;
        this.assistingNurses = new ArrayList<>();
    }

    public Operation.Status getStatus() { return status; }
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }
    public OperationRoom getOperationRoom() { return operationRoom; }
    public Doctor getSurgeon() { return surgeon; }
    public List<Nurse> getAssistingNurses() { return assistingNurses; }
    public Patient getPatient() { return patient; }

    public void setStatus(Operation.Status status) { this.status = status; }
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }
    public void setOperationRoom(OperationRoom operationRoom) {
        if (this.operationRoom != null) {
            this.operationRoom.removeOperation(this);
        }

        this.operationRoom = operationRoom;

        if (operationRoom != null) {
            operationRoom.addOperation(this);
        }
    }
    public void setSurgeon(Doctor surgeon) {
        if (this.surgeon != null) {
            this.surgeon.removeOperation(this);
        }

        this.surgeon = surgeon;

        if (surgeon != null) {
            surgeon.addOperation(this);
        }
    }
    public void setAssistingNurses(List<Nurse> assistingNurses) { this.assistingNurses = assistingNurses; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public void addAssistingNurse(Nurse nurse) {
        if (nurse != null && !assistingNurses.contains(nurse)) {
            assistingNurses.add(nurse);
            if (!nurse.getOperations().contains(this)) {
                nurse.getOperations().add(this);
            }
        }
    }

    public void removeAssistingNurse(Nurse nurse) {
        if (assistingNurses.remove(nurse)) {
            nurse.getOperations().remove(this);
        }
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
