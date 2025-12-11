package models;

import annotations.NotNull;

import java.sql.Date;

public class Therapy extends Treatment {

    public enum TherapyType {
        Chemo,
        Radiation,
        Hormone
    }

    @NotNull
    private TherapyType type;

    public Therapy() {}

    public Therapy(String name, double dose, Date startDate, Date endDate, TreatmentHistory treatmentHistory, TherapyType type) {
        super(name, dose, startDate, endDate, treatmentHistory);
        this.type = type;
    }

    public TherapyType getType() {
        return type;
    }

    public void setType(TherapyType type) {
        this.type = type;
    }
}
