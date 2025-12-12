package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import exceptions.NumberOverflowException;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class PatientAmbulanceTransit {
    private static Long total = 0L;
    public enum Status { HEADING_TO_PATIENT, ARRIVED, HEADING_TO_HOSPITAL, DELIVERED }

    @NotNull
    private Long id;

    @NotNull
    private Status status;

    @NotNull
    private Timestamp registered;

    @NotNull
    @NotEmpty
    private String pickLocation;

    @NotNull
    @NotEmpty
    @JsonDeserialize(as = java.util.ArrayList.class)
    private List<Patient> listOfPatientsTransported;

    @NotNull
    private AmbulanceVehicle ambulanceVehicle;

    public PatientAmbulanceTransit() {}
    public PatientAmbulanceTransit(Status status, Timestamp registered, String pickLocation, AmbulanceVehicle ambulanceVehicle, List<Patient> listOfPatientsTransported) {
        setId(this);
        this.status = status;
        this.registered = registered;
        this.pickLocation = pickLocation;
        this.listOfPatientsTransported = new ArrayList<>();
        this.registerTransit(listOfPatientsTransported, ambulanceVehicle);
    }

    private static void setId(PatientAmbulanceTransit patientAmbulanceTransit) {
        patientAmbulanceTransit.id = total++;
    }

    public Long getId() { return id; }
    public Status getStatus() { return status; }
    public Timestamp getRegistered() { return registered; }
    public String getPickLocation() { return pickLocation; }
    public List<Patient> getListOfPatientsTransported() { return listOfPatientsTransported; }
    public AmbulanceVehicle getAmbulanceVehicle() { return ambulanceVehicle; }

    public void setStatus(Status status) { this.status = status; }
    public void setRegistered(Timestamp registered) { this.registered = registered; }
    public void setPickLocation(String pickLocation) { this.pickLocation = pickLocation; }
    public void setListOfPatientsTransported(List<Patient> listOfPatientsTransported) {  this.listOfPatientsTransported = listOfPatientsTransported; }
    public void setAmbulanceVehicle(AmbulanceVehicle ambulanceVehicle) {  this.ambulanceVehicle = ambulanceVehicle; }

    private void registerTransit(List<Patient> patientList, AmbulanceVehicle ambulanceVehicle) {
        if (ambulanceVehicle.getPersonLimit() < patientList.size()) throw new NumberOverflowException(AmbulanceVehicle.class.getName() + " cannot have more than " + ambulanceVehicle.getPersonLimit() + " people!");

        for (Patient patient : patientList) patient.addPatientAmbulanceTransit(this);
        this.listOfPatientsTransported = new ArrayList<>(patientList);

        ambulanceVehicle.addPatientAmbulanceTransit(this);
        this.ambulanceVehicle = ambulanceVehicle;
    }

    private void removeTransit() {
        for (Patient patient : this.listOfPatientsTransported) patient.removePatientAmbulanceTransit(this);
        ambulanceVehicle.removePatientAmbulanceTransit(this);

        this.listOfPatientsTransported = null;
        this.ambulanceVehicle = null;
    }
}
