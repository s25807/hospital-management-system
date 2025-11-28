package models;

import annotations.NotEmpty;
import annotations.NotNull;
import annotations.ValidDate;

import java.sql.Date;

public class AdditionalInformation {

    @NotNull
    @ValidDate(value = ValidDate.Mode.PAST)
    private Date dateAddition;

    @NotNull
    @NotEmpty
    private String information;

    @NotNull
    private ImportanceLevel importance;

    public enum ImportanceLevel {
        Low, Medium, High
    }

    public AdditionalInformation() {}

    public AdditionalInformation(Date dateAddition, String information, ImportanceLevel importance) {
        this.dateAddition = dateAddition;
        this.information = information;
        this.importance = importance;
    }

    public Date getDateAddition() {
        return dateAddition;
    }

    public void setDateAddition(Date dateAddition) {
        this.dateAddition = dateAddition;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public ImportanceLevel getImportance() {
        return importance;
    }

    public void setImportance(ImportanceLevel importance) {
        this.importance = importance;
    }
}
