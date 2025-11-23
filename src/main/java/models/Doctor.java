package models;

import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.List;

public class Doctor extends Employee {
    @NotNull
    private boolean hasHeadRole;

    public Doctor() {}
    public Doctor(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty,  boolean hasHeadRole) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty);
        this.hasHeadRole = hasHeadRole;
    }
    public Doctor(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, List<String> listOfMedLicenceNumbers,boolean hasHeadRole) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, listOfMedLicenceNumbers);
        this.hasHeadRole = hasHeadRole;
    }

    public boolean isHasHeadRole() { return hasHeadRole; }
    public void assignHeadRole(boolean hasHeadRole) { this.hasHeadRole = hasHeadRole; }

    @JsonIgnore
    public void newOperation() {}

    @JsonIgnore
    public void viewOperation() {}

    @JsonIgnore
    public void editOperation() {}
}
