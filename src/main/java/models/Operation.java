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

    private OperationRoom operationRoom;

    @NotNull
    @NotEmpty
    @JsonDeserialize(as = ArrayList.class)
    private List<Patient> patients;

    @NotNull
    @NotEmpty
    @JsonDeserialize(as = ArrayList.class)
    private List<Doctor> doctors;

    @NotNull
    @NotEmpty
    @JsonDeserialize(as = ArrayList.class)
    private List<Nurse> nurses;

    public Operation() {
        nurses = new ArrayList<>();
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
    }
    public Operation(Timestamp startTime) {
        this.startTime = startTime;
        this.status = Status.Preparation;
        nurses = new ArrayList<>();
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
    }

    public Operation.Status getStatus() { return status; }
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }
    public OperationRoom getOperationRoom() { return operationRoom; }
    public List<Patient> getPatients() { return patients; }
    public List<Doctor> getDoctors() { return doctors; }
    public List<Nurse> getNurses() { return nurses; }

    public void setStatus(Operation.Status status) { this.status = status; }
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }
    public void setPatients(List<Patient> patients) { this.patients = patients; }
    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }
    public void setNurses(List<Nurse> nurses) { this.nurses = new ArrayList<>(nurses); }
    public void setOperationRoom(OperationRoom operationRoom) {
        if (this.operationRoom != null) {
            this.operationRoom.removeOperation(this);
            if (operationRoom == null) this.operationRoom = null;
        }
        if (operationRoom != null && this.operationRoom != operationRoom) {
            this.operationRoom = operationRoom;
            this.operationRoom.addOperation(this);
        }
    }

    public void addPatient(Patient patient) {
        if (patient != null && !patients.contains(patient)) {
            patients.add(patient);
            patient.addOperation(this);
        }
    }

    public void addDoctor(Doctor doctor) {
        if (doctor != null && !doctors.contains(doctor)) {
            doctors.add(doctor);
            doctor.addOperation(this);
        }
    }

    public void addNurse(Nurse nurse) {
        if (nurse != null && !nurses.contains(nurse)) {
            nurses.add(nurse);
            nurse.addOperation(this);
        }
    }

    public void removePatient(Patient patient) {
        if (patient != null && patients.contains(patient)) {
            patient.removeOperation(this);
            patients.remove(patient);
        }
    }

    public void removeDoctor(Doctor doctor) {
        if (doctor != null && doctors.contains(doctor)) {
            doctor.removeOperation(this);
            doctors.remove(doctor);
        }
    }

    public void removeNurse(Nurse nurse) {
        if (nurse != null && nurses.contains(nurse)) {
            nurse.removeOperation(this);
            nurses.remove(nurse);
        }
    }

    public void removeOperationRoom(OperationRoom operationRoom) {
        this.operationRoom = null;
        if (operationRoom.hasOperation(this)) operationRoom.removeOperation(this);
    }

    public boolean hasPatient(Patient patient) { return patients.contains(patient); }
    public boolean hasDoctor(Doctor doctor) { return doctors.contains(doctor); }
    public boolean hasNurse(Nurse nurse) { return nurses.contains(nurse); }

    /**
     *
     * @return time duration in minutes
     */
    public long calculateDuration() {
        long diffMillis = endTime.getTime() - startTime.getTime();
        return diffMillis / 60000;
    }
}