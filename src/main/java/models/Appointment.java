package models;

import java.sql.Timestamp;

import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
public class Appointment {
    public enum Status { Initialized, Scheduled, InProgress, Cancelled, Completed };

    @NotNull
    private Timestamp startTime;

    @NotNull
    private Timestamp endTime;

    @NotNull
    private Status status;

    @NotNull
    private Patient patient;

    @NotNull
    private Doctor doctor;

    private Nurse nurse;

    public Appointment() {}

    public Appointment(Timestamp startTime) {
        this.startTime = startTime;
        this.status = Status.Initialized;
    }

    public Status getStatus() { return status; }
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public Nurse getNurse() { return nurse; }

    public void setStatus(Status status) { this.status = status; }
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }
    public void setPatient(Patient patient) {
        if (this.patient != null) {
            this.patient.removeAppointment(this);
        }

        this.patient = patient;

        if (patient != null) {
            patient.addAppointment(this);
        }
    }
    public void setDoctor(Doctor doctor) {
        if (this.doctor != null) {
            this.doctor.removeAppointment(this);
        }

        this.doctor = doctor;

        if (doctor != null) {
            doctor.addAppointment(this);
        }
    }
    public void setNurse(Nurse nurse) {
        if (this.nurse != null) {
            this.nurse.removeAppointment(this);
        }

        this.nurse = nurse;

        if (nurse != null) {
            nurse.addAppointment(this);
        }
    }

    public long calculateDuration() {
        long diffMillis = endTime.getTime() - startTime.getTime();
        return diffMillis / 60000;
    }
}
