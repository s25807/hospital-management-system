package models;

import annotations.NotEmpty;
import annotations.NotNull;
import annotations.ValidDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.sql.Date;
import java.time.LocalDate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
public class MedicalLicense {

    @NotNull
    @NotEmpty
    private String licenseNumber;

    @NotNull
    @ValidDate(value = ValidDate.Mode.PAST)
    private Date acquisitionDate;

    @NotNull
    @ValidDate(value = ValidDate.Mode.FUTURE)
    private Date expirationDate;

    public MedicalLicense() {}
    public MedicalLicense(String licenseNumber, Date acquisitionDate, Date expirationDate) {
        setLicenseNumber(licenseNumber);
        this.acquisitionDate = acquisitionDate;
        this.expirationDate = expirationDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("License number must not be null or empty.");
        }
        this.licenseNumber = licenseNumber;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @JsonIgnore
    public boolean isExpired() { return expirationDate.toLocalDate().isBefore(LocalDate.now()); }
}
