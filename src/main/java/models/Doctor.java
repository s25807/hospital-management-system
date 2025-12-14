package models;

import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Doctor extends Employee {
    @NotNull
    private boolean hasHeadRole;

    private List<Specialization> specializations;
    private Doctor supervisor;

    @JsonDeserialize(as = ArrayList.class)
    private List<Doctor> supervisorsList;

    public Doctor() {
        this.specializations = new ArrayList<>();
        this.supervisorsList = new ArrayList<>();
    }
    public Doctor(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty,  boolean hasHeadRole, MedicalLicense medicalLicense) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, medicalLicense);
        this.hasHeadRole = hasHeadRole;
        this.specializations = new ArrayList<>();
        this.supervisorsList = new ArrayList<>();
    }

    public boolean isHasHeadRole() { return hasHeadRole; }
    public List<Specialization> getSpecializations() { return specializations; }
    public Doctor getSupervisor() { return supervisor; }
    public List<Doctor> getSupervisorsList() { return supervisorsList; }

    public void assignHeadRole(boolean hasHeadRole) { this.hasHeadRole = hasHeadRole; }
    public void setSpecializations(List<Specialization> specializations) { this.specializations = specializations; }
    public void addSpecialization(Specialization specialization) { this.specializations.add(specialization); }
    public void removeSpecialization(Specialization specialization) { this.specializations.remove(specialization); }
    public void setSupervisor(Doctor doctor) {
        if(doctor != this &&  doctor != null) {
            supervisor = doctor;
            if(!doctor.isSupervising(this)) supervisor.addDoctorHeSupervisors(this);
        }
        else if (doctor == null) supervisor = null;
        else throw new IllegalArgumentException("Doctor cannot be his own supervisor");
    }
    public void addDoctorHeSupervisors(Doctor doctor) {
        if(supervisor != this) {
            if(!doctor.isSupervising(this)) supervisorsList.add(doctor);
            doctor.setSupervisor(this);
        }
    }

    @JsonIgnore
    public boolean isSupervising(Doctor doctor) {
        if (doctor != null) return supervisorsList.contains(doctor);
        else return false;
    }

    public void removeSupervisor() {
        if(supervisor.isSupervising(this)) supervisor.removeDoctorHeSupervisors(this);
        supervisor = null;
    }
    public void removeDoctorHeSupervisors(Doctor doctor) {
        supervisorsList.remove(doctor);
        doctor.setSupervisor(null);
    }

    @JsonIgnore
    public void newOperation() {}

    @JsonIgnore
    public void viewOperation() {}

    @JsonIgnore
    public void editOperation() {}
}
