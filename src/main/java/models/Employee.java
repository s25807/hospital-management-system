package models;

import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Employee extends Person {
    enum Status { ACTIVE, ON_LEAVE, INACTIVE }

    @NotNull
    @NotEmpty
    private String employeeId;

    @NotNull
    private Status status;

    @NotNull
    private boolean onDuty;

    @NotNull
    private List<String> listOfMedLicenceNumbers;

    public Employee() {}
    public Employee(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty) {
        super(pesel, username, password, name, surname, dob, nationality);
        this.employeeId = employeeId;
        this.status = status;
        this.onDuty = onDuty;
        this.listOfMedLicenceNumbers = new ArrayList<>();
    }
    public Employee(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty,  List<String> listOfMedLicenceNumbers) {
        super(pesel, username, password, name, surname, dob, nationality);
        this.employeeId = employeeId;
        this.status = status;
        this.onDuty = onDuty;
        this.listOfMedLicenceNumbers =  listOfMedLicenceNumbers;
    }

    public String getEmployeeId() { return employeeId; }
    public Status getStatus() { return status; }
    public boolean isOnDuty() { return onDuty; }
    public List<String> getListOfMedLicenceNumbers() { return listOfMedLicenceNumbers; }

    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setStatus(Status status) { this.status = status; }
    public void toggleDuty(boolean onDuty) { this.onDuty = onDuty; }

    @JsonIgnore
    public void registerNewMedLicence(String medLicenceNumber) { this.listOfMedLicenceNumbers.add(medLicenceNumber); }

    @JsonIgnore
    public Optional<String> findMedicalLicenseNumber(String medLicenceNumber) {
        for (String num :  listOfMedLicenceNumbers) if (medLicenceNumber.equals(num)) return Optional.of(num);
        return Optional.empty();
    }
}
