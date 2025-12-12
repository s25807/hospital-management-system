package models;

import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Paramedic extends Employee{
    public enum LicenceType { FIRST_AID, SURGICAL }

    @NotNull
    private LicenceType licenceType;

    @NotNull
    @NotEmpty
    private String licenceNumber;

    @NotNull
    private boolean hasEmergencyDrivingPermit;

    @NotNull
    @NotEmpty
    private String cprNumber;

    @NotNull
    @NotEmpty
    private String advancedLifeSupNumber;

    @NotNull
    private AmbulanceVehicle vehicle;

    public Paramedic() {}
    public Paramedic(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, MedicalLicense medicalLicense ,LicenceType licenceType, String licenceNumber, boolean hasEmergencyDrivingPermit, String cprNumber, String advancedLifeSupNumber, AmbulanceVehicle vehicle) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, medicalLicense);
        this.licenceType = licenceType;
        this.licenceNumber = licenceNumber;
        this.hasEmergencyDrivingPermit = hasEmergencyDrivingPermit;
        this.cprNumber = cprNumber;
        this.advancedLifeSupNumber = advancedLifeSupNumber;
        this.vehicle = vehicle;
    }
    public Paramedic(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality, String employeeId, Status status, boolean onDuty, Map<String, MedicalLicense> mapOfMedicalLicenseNumbers, LicenceType licenceType, String licenceNumber, boolean hasEmergencyDrivingPermit, String cprNumber, String advancedLifeSupNumber, AmbulanceVehicle vehicle) {
        super(pesel, username, password, name, surname, dob, nationality, employeeId, status, onDuty, mapOfMedicalLicenseNumbers);
        this.licenceType = licenceType;
        this.licenceNumber = licenceNumber;
        this.hasEmergencyDrivingPermit = hasEmergencyDrivingPermit;
        this.cprNumber = cprNumber;
        this.advancedLifeSupNumber = advancedLifeSupNumber;
        this.vehicle = vehicle;
    }

    public LicenceType getLicenceType() { return this.licenceType; }
    public String getLicenceNumber() { return this.licenceNumber; }
    public boolean isHasEmergencyDrivingPermit() { return this.hasEmergencyDrivingPermit; }
    public String getCprNumber() { return this.cprNumber; }
    public String getAdvancedLifeSupNumber() { return this.advancedLifeSupNumber; }
    public AmbulanceVehicle getVehicle() { return this.vehicle; }

    public void setLicenceType(LicenceType licenceType) { this.licenceType = licenceType; }
    public void setLicenceNumber(String licenceNumber) { this.licenceNumber = licenceNumber; }
    public void setHasEmergencyDrivingPermit(boolean hasEmergencyDrivingPermit) { this.hasEmergencyDrivingPermit = hasEmergencyDrivingPermit; }
    public void setCprNumber(String cprNumber) { this.cprNumber = cprNumber; }
    public void setAdvancedLifeSupNumber(String  advancedLifeSupNumber) { this.advancedLifeSupNumber = advancedLifeSupNumber; }
    public void setVehicle(AmbulanceVehicle vehicle) {
        if (!vehicle.isAssignedParamedic(this)) {
            if (this.vehicle != null) this.vehicle.removeParamedic(this);
            vehicle.addParamedic(this);
        }
        this.vehicle = vehicle;
    }

    @JsonIgnore
    public void searchPatient() {}

    @JsonIgnore
    public boolean isAssigned(AmbulanceVehicle ambulanceVehicle) { return ambulanceVehicle == this.vehicle; }
}
