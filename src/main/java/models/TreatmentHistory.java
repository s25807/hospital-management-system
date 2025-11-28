package models;

import annotations.NotNull;
import annotations.ValidDate;

import java.sql.Date;

public class TreatmentHistory {

    @NotNull
    private Patient patient;

    @NotNull
    private Treatment treatment;

    @NotNull
    private AdditionalInformation additionalInformation;

    @NotNull
    @ValidDate(value = ValidDate.Mode.PAST)
    private Date dateRecorded;

    public TreatmentHistory() {}

    public TreatmentHistory(Patient patient, Treatment treatment, AdditionalInformation additionalInformation, Date dateRecorded) {
        this.patient = patient;
        this.treatment = treatment;
        this.additionalInformation = additionalInformation;
        this.dateRecorded = dateRecorded;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public AdditionalInformation getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(AdditionalInformation additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }
}
