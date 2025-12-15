package models;

import annotations.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

public class PatientRoom extends Room{
    @NotNull
    private boolean vip;

    @NotNull
    @JsonDeserialize(as = ArrayList.class)
    private List<Patient> patients;

    public static final double vipPrice = 1000;

    public PatientRoom() {
        this.patients = new ArrayList<>();
    }
    public PatientRoom(String roomNumber, int maxPeopleAllowed, int occupancy,  boolean vip, Floor floor) {
        super(roomNumber, maxPeopleAllowed, occupancy, floor);
        this.vip = vip;
        this.patients = new ArrayList<>();
    }

    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }

    @Override
    public void destroyRoom() {
        super.destroyRoom();
        for (Patient p : new ArrayList<>(patients)) {
            p.setPatientRoom(null);
        }
        patients.clear();
    }

    public List<Patient> getPatients() { return patients; }
    public void setPatients(List<Patient> patients) { this.patients = patients; }

    public void addPatient(Patient patient) {
        if (patient != null && !hasPatient(patient)) {
            patients.add(patient);
            if (patient.getPatientRoom() != this) patient.setPatientRoom(this);
        }
    }

    public void removePatient(Patient patient) {
        if (patient != null && hasPatient(patient)) {
            patients.remove(patient);
            if (patient.getPatientRoom() == this) patient.setPatientRoom(null);
        }
    }

    public boolean hasPatient(Patient patient) {
        return patients.contains(patient);
    }

}
