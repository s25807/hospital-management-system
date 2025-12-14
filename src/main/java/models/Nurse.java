package models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import annotations.NotNull;

public class Nurse extends Employee {

    @NotNull
    private Department department;
    @NotNull
    @JsonDeserialize(as = ArrayList.class)
    private List<Operation> operations;

    public Nurse() {
        this.operations = new ArrayList<>();
    }
    public Nurse(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, MedicalLicense medicalLicense) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, medicalLicense);
        this.operations = new ArrayList<>();
    }
    public Nurse(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, Map<String, MedicalLicense> mapOfMedLicenceNumbers) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, mapOfMedLicenceNumbers);
        this.operations = new ArrayList<>();
    }

    public Department getDepartment() { return department; }
    public List<Operation> getOperations() { return operations; }
    public void setOperations(List<Operation> operations) { this.operations = operations; }

    public void addOperation(Operation operation) {
        if (operation != null && !operations.contains(operation)) {
            operations.add(operation);
            if (!operation.getAssistingNurses().contains(this)) {
                operation.getAssistingNurses().add(this);
            }
        }
    }

    public void removeOperation(Operation operation) {
        if (operations.remove(operation)) {
            operation.getAssistingNurses().remove(this);
        }
    }
    public void setDepartment(Department department) {
        if (this.department != null) {
            this.department.removeNurse(this);
        }
    
        this.department = department;

        if (department != null) {
            department.addNurse(this);
        }
    }

    @JsonIgnore
    public void newPatientAssignment() {}

    @JsonIgnore
    public void viewPatientAssignment() {}

    @JsonIgnore
    public void editPatientAssignment() {}
}
