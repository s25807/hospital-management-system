package models;

import annotations.Min;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    public enum BloodType { A, B, AB, O };

    @NotNull
    private BloodType bloodType;

    @NotNull
    private boolean insurance;

    @NotNull
    @Min(value = 0.1)
    private double weight;

    @NotNull
    @Min(value = 0.1)
    private double height;

    @NotNull
    private boolean isActive;

    @NotNull
    private TreatmentHistory treatmentHistory;

    @NotNull
    @JsonDeserialize(as = ArrayList.class)
    private List<PatientAmbulanceTransit> patientAmbulanceTransitList;

    @NotNull
    @JsonDeserialize(as = ArrayList.class)
    private List<Operation> operations;

    public Patient() { patientAmbulanceTransitList = new ArrayList<>(); operations = new ArrayList<>(); }
    public Patient(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, BloodType bloodType, boolean insurance, double weight, double height, boolean isActive) {
        super(pesel, username, password, name, surname, dob, nationality);
        this.bloodType = bloodType;
        this.insurance = insurance;
        this.weight = weight;
        this.height = height;
        this.isActive = isActive;
        this.treatmentHistory = new TreatmentHistory();
        this.patientAmbulanceTransitList = new ArrayList<>();
        this.operations = new ArrayList<>();
    }

    public BloodType getBloodType() { return bloodType; }
    public boolean getInsurance() { return insurance; }
    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public boolean getIsActive() { return isActive; }
    public TreatmentHistory getTreatmentHistory() { return treatmentHistory; }
    public List<PatientAmbulanceTransit> getPatientAmbulanceTransitList() { return this.patientAmbulanceTransitList; }
    public List<Operation> getOperations() { return this.operations; }

    public void setBloodType(BloodType bloodType) { this.bloodType = bloodType; }
    public void setInsurance(boolean insurance) { this.insurance = insurance; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setHeight(double height) { this.height = height; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
    public void setTreatmentHistory(TreatmentHistory treatmentHistory) {  this.treatmentHistory = treatmentHistory; }
    public void setPatientAmbulanceTransitList(List<PatientAmbulanceTransit> patientAmbulanceTransitIdList) {  this.patientAmbulanceTransitList = patientAmbulanceTransitIdList; }
    public void setOperations(List<Operation> operations) { this.operations = operations; }

    public void createAppointment() {}

    public void addPatientAmbulanceTransit(PatientAmbulanceTransit patientAmbulanceTransit) {
        if (!this.patientAmbulanceTransitList.contains(patientAmbulanceTransit)) {
            this.patientAmbulanceTransitList.add(patientAmbulanceTransit);
        }
    }

    public void removePatientAmbulanceTransit(PatientAmbulanceTransit patientAmbulanceTransit) {
        this.patientAmbulanceTransitList.remove(patientAmbulanceTransit);
    }

    public void addOperation(Operation operation) {
        if (operation != null &&  !operations.contains(operation)) {
            operations.add(operation);
            if (operation.hasPatient(this)) operation.addPatient(this);
        }
    }

    public void removeOperation(Operation operation) {
        if (operation != null && operations.contains(operation)) {
            operations.remove(operation);
            if (operation.hasPatient(this)) operation.removePatient(this);
        }
    }
}
