package models;

import annotations.NotNull;
import annotations.ValidDate;

import java.sql.Date;
import java.util.List;

public class TreatmentHistory {

    @NotNull
    private Patient patient;

    @NotNull
    private List<Treatment> treatmentList;

    @NotNull
    private List<AdditionalInformation> additionalInformationList;

    @NotNull
    @ValidDate(value = ValidDate.Mode.PAST)
    private Date dateRecorded;

    public TreatmentHistory() {}

    public TreatmentHistory(Patient patient, List<Treatment> treatmentList, List<AdditionalInformation> additionalInformationList, Date dateRecorded) {
        this.patient = patient;
        this.treatmentList = treatmentList;
        this.additionalInformationList = additionalInformationList;
        this.dateRecorded = dateRecorded;
    }

    public Patient getPatient() {
        return patient;
    }
    public Date getDateRecorded() {
        return dateRecorded;
    }
    public List<Treatment> getTreatmentList() {
        return treatmentList;
    }
    public List<AdditionalInformation> getAdditionalInformationList() {
        return additionalInformationList;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public void setTreatmentList(List<Treatment> treatmentList) {
        this.treatmentList = treatmentList;
    }
    public void setAdditionalInformationList(List<AdditionalInformation> additionalInformationList) { this.additionalInformationList = additionalInformationList; }
    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public void addTreatment(Treatment treatment) {
        if(treatment == null) throw new NullPointerException("Treatment cannot be null");
        if(treatment.getHistory() != this) treatment.setHistory(this);
        if(!treatmentList.contains(treatment)) treatmentList.add(treatment);
    }

    public void removeTreatment(Treatment treatment) {
        if(treatment == null) throw new NullPointerException("Treatment cannot be null");
        if(treatment.getHistory() == this) setTreatmentList(null);
        treatmentList.remove(treatment);
    }
}
