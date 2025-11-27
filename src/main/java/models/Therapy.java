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

    public Therapy() {
        super();
    }

    public Therapy(String name, double dose, Date startDate, Date endDate, TherapyType type) {
        super(name, dose, startDate, endDate);
        this.type = type;
    }

    public TherapyType getType() {
        return type;
    }

    public void setType(TherapyType type) {
        this.type = type;
    }
}
