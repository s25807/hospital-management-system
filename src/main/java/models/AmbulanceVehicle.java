package models;

import annotations.Min;
import annotations.NotNull;

public abstract class AmbulanceVehicle {
    public enum Brand { REV, DEMERS, BINZ, ICU };

    @NotNull
    private String registrationPlate;

    @NotNull
    private Brand brand;

    @Min(value = 0.1)
    @NotNull
    private double weightLimit;

    @NotNull
    private int personLimit;

    @NotNull
    private boolean isOnMission;

    @NotNull
    private double maxSpeed;

    @NotNull
    private double rangeOfTravel;

    public AmbulanceVehicle(String registrationPlate, Brand brand, double weightLimit, int personLimit, boolean isOnMission, double maxSpeed, double rangeOfTravel) {
        this.registrationPlate = registrationPlate;
        this.brand = brand;
        this.weightLimit = weightLimit;
        this.personLimit = personLimit;
        this.isOnMission = isOnMission;
        this.maxSpeed = maxSpeed;
        this.rangeOfTravel = rangeOfTravel;
    }

    public String getRegistrationPlate() { return registrationPlate; }
    public Brand getBrand() { return brand; }
    public double getWeightLimit() { return weightLimit; }
    public int getPersonLimit() { return personLimit; }
    public boolean isOnMission() { return isOnMission; }
    public double getMaxSpeed() { return maxSpeed; }
    public double getRangeOfTravel() { return rangeOfTravel; }

    public void setRegistrationPlate(String registrationPlate) { this.registrationPlate = registrationPlate; }
    public void setBrand(Brand brand) { this.brand = brand; }
    public void setWeightLimit(double weightLimit) { this.weightLimit = weightLimit; }
    public void setPersonLimit(int personLimit) { this.personLimit = personLimit; }
    public void setOnMission(boolean onMission) { this.isOnMission = onMission; }
    public void setMaxSpeed(double maxSpeed) { this.maxSpeed = maxSpeed; }
    public void setRangeOfTravel(double rangeOfTravel) { this.rangeOfTravel = rangeOfTravel; }


}
