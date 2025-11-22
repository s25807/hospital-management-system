package models;

import annotations.NotNull;
import annotations.ValidDate;

import java.time.LocalDate;

public class MedicalLicense {

    @NotNull
    private String licenseNumber;

    @NotNull
    @ValidDate(value = ValidDate.Mode.PAST)
    private LocalDate acquisitionDate;

    @NotNull
    @ValidDate(value = ValidDate.Mode.FUTURE)
    private LocalDate expirationDate;

    public MedicalLicense(String licenseNumber, LocalDate acquisitionDate, LocalDate expirationDate) {
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

    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }
}
