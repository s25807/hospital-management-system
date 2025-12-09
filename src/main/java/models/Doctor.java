package models;

import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Doctor extends Employee {
    @NotNull
    private boolean hasHeadRole;

    private Doctor supervisor;

    private List<Doctor> supervisorsList;

    public Doctor() {}
    public Doctor(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty,  boolean hasHeadRole, MedicalLicense medicalLicense) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, medicalLicense);
        this.hasHeadRole = hasHeadRole;
        this.supervisorsList = new ArrayList<Doctor>();
    }
    public Doctor(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty,boolean hasHeadRole, Map<String, MedicalLicense> mapOfMedLicenceNumbers) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, mapOfMedLicenceNumbers);
        this.hasHeadRole = hasHeadRole;
        this.supervisorsList = new ArrayList<Doctor>();
    }

    public boolean isHasHeadRole() { return hasHeadRole; }
    public Doctor getSupervisor() { return supervisor; }
    public List<Doctor> getSupervisorsList() { return new ArrayList<>(supervisorsList); }

    public void assignHeadRole(boolean hasHeadRole) { this.hasHeadRole = hasHeadRole; }
    public void setSupervisor(Doctor supervisor) {
        if(supervisor != this) this.supervisor = supervisor;
        else throw new IllegalArgumentException("Doctor cannot be his own supervisor");
    }
    public void addDoctorHeSupervisors(Doctor doctor) { this.supervisorsList.add(doctor); }

    @JsonIgnore
    public void newOperation() {}

    @JsonIgnore
    public void viewOperation() {}

    @JsonIgnore
    public void editOperation() {}
}
