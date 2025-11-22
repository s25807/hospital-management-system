package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.List;

public class Nurse extends Employee {

    public Nurse() {}
    public Nurse(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty);
    }
    public Nurse(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, List<String> listOfMedLicenceNumbers) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, listOfMedLicenceNumbers);
    }

    @JsonIgnore
    public void newPatientAssignment() {}

    @JsonIgnore
    public void viewPatientAssignment() {}

    @JsonIgnore
    public void editPatientAssignment() {}
}
