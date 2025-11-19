package models;

import annotations.Min;
import annotations.NotNull;

import java.sql.Date;

public class Patient extends Person {
    public enum BloodType { A, B, AB, O };

    @NotNull
    private BloodType bloodType;

    @NotNull
    private boolean insurance;

    @NotNull
    @Min(value = 0.1)
    private double weight;

    @NotNull
    @Min(value = 0.1)
    private double height;

    @NotNull
    private boolean isActive;

    public Patient() {}
    public Patient(String pesel, String name, String surname, Date dob, Nation nationality, BloodType bloodType, boolean insurance, double weight, double height, boolean isActive) {
        super(pesel, name, surname, dob, nationality);
        this.bloodType = bloodType;
        this.insurance = insurance;
        this.weight = weight;
        this.height = height;
        this.isActive = isActive;
    }

    public BloodType getBloodType() { return bloodType; }
    public boolean getInsurance() { return insurance; }
    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public boolean getIsActive() { return isActive; }

    public void setBloodType(BloodType bloodType) { this.bloodType = bloodType; }
    public void setInsurance(boolean insurance) { this.insurance = insurance; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setHeight(double height) { this.height = height; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
