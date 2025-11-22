package models;

import java.time.LocalDate;

public class MedicalLicense {
    private String licenseNumber;
    private LocalDate acquisitionDate;
    private LocalDate expirationDate;

    public MedicalLicense(String licenseNumber, LocalDate acquisitionDate, LocalDate expirationDate) {
        setLicenseNumber(licenseNumber);
        validateDates(acquisitionDate, expirationDate);
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
        validateDates(acquisitionDate, this.expirationDate);
        this.acquisitionDate = acquisitionDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        validateDates(this.acquisitionDate, expirationDate);
        this.expirationDate = expirationDate;
    }

    private void validateDates(LocalDate acquisition, LocalDate expiration) {
        if (acquisition == null || expiration == null) {
            throw new IllegalArgumentException("Acquisition and expiration dates must not be null.");
        }
        if (!expiration.isAfter(acquisition)) {
            throw new IllegalArgumentException(
                    "Expiration date must be after acquisition date. " +
                            "(acquisition=" + acquisition + ", expiration=" + expiration + ")"
            );
        }
    }

    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }
}
