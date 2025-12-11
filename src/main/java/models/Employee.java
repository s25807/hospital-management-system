package models;

import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.Date;
import java.util.*;

public abstract class Employee extends Person {
    public enum Status { ACTIVE, ON_LEAVE, INACTIVE }

    @NotNull
    @NotEmpty
    private String employeeId;

    @NotNull
    private Status status;

    @NotNull
    private boolean onDuty;

    @NotNull
    @NotEmpty
    @JsonDeserialize(as = HashMap.class)
    private Map<String, MedicalLicense> mapOfMedLicenceNumbers;

    public Employee() {}
    public Employee(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, MedicalLicense medicalLicense) {
        super(pesel, username, password, name, surname, dob, nationality);
        this.employeeId = employeeId;
        this.status = status;
        this.onDuty = onDuty;
        this.mapOfMedLicenceNumbers = new HashMap<>();
        registerNewMedLicence(medicalLicense);
    }
    public Employee(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty,  Map<String, MedicalLicense> mapOfMedLicenceNumbers) {
        super(pesel, username, password, name, surname, dob, nationality);
        this.employeeId = employeeId;
        this.status = status;
        this.onDuty = onDuty;
        this.mapOfMedLicenceNumbers =  mapOfMedLicenceNumbers;
    }

    public String getEmployeeId() { return employeeId; }
    public Status getStatus() { return status; }
    public boolean isOnDuty() { return onDuty; }
    public Map<String, MedicalLicense> getMapOfMedLicenceNumbers() { return new HashMap<>(mapOfMedLicenceNumbers); }

    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setStatus(Status status) { this.status = status; }
    public void toggleDuty(boolean onDuty) { this.onDuty = onDuty; }

    @JsonIgnore
    public void registerNewMedLicence(MedicalLicense medicalLicense) {
        this.mapOfMedLicenceNumbers.put(medicalLicense.getLicenseNumber(),  medicalLicense);
    }

    @JsonIgnore
    public Optional<String> findMedicalLicenseNumber(String medLicenceNumber) {
        if (mapOfMedLicenceNumbers.containsKey(medLicenceNumber)) return Optional.of(medLicenceNumber);
        return Optional.empty();
    }
}
