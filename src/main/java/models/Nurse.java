package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import annotations.NotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Nurse extends Employee {

    @NotNull
    private Department department;

    @NotNull
    private List<Operation> operations;

    public Nurse() { operations = new ArrayList<>(); }
    public Nurse(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, MedicalLicense medicalLicense) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, medicalLicense);
        operations = new ArrayList<>();
    }
    public Nurse(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, Map<String, MedicalLicense> mapOfMedLicenceNumbers) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, mapOfMedLicenceNumbers);
        operations = new ArrayList<>();
    }

    public Department getDepartment() { return department; }
    public List<Operation>  getOperations() { return operations; }

    public void setOperations(List<Operation> operations) { this.operations = operations; }
    public void setDepartment(Department department) {
        if (this.department != null) {
            this.department.removeNurse(this);
        }
    
        this.department = department;

        if (department != null) {
            department.addNurse(this);
        }
    }

    public void addOperation(Operation operation) {
        if(operation != null && !operations.contains(operation)) {
            operations.add(operation);
            if (operation.hasNurse(this)) operation.addNurse(this);
        }
    }

    public void removeOperation(Operation operation) {
        if(operation != null && operations.contains(operation)) {
            operations.remove(operation);
            if (operation.hasNurse(this)) operation.removeNurse(this);
        }
    }

    @JsonIgnore
    public void newPatientAssignment() {}

    @JsonIgnore
    public void viewPatientAssignment() {}

    @JsonIgnore
    public void editPatientAssignment() {}
}
