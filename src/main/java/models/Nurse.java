package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Nurse extends Employee {

    public Nurse() {}
    public Nurse(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, MedicalLicense medicalLicense) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, medicalLicense);
    }
    public Nurse(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, Map<String, MedicalLicense> mapOfMedLicenceNumbers) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, mapOfMedLicenceNumbers);
    }

    @JsonIgnore
    public void newPatientAssignment() {}

    @JsonIgnore
    public void viewPatientAssignment() {}

    @JsonIgnore
    public void editPatientAssignment() {}
}
