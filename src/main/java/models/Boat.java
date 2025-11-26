package models;

import annotations.NotNull;

public class Boat extends AmbulanceVehicle {
    @NotNull
    private double length;

    public Boat() {}
    public Boat(String registrationPlate, Brand brand, double weightLimit, int personLimit, boolean isOnMission, double maxSpeed, double rangeOfTravel, double length) {
        super(registrationPlate, brand, weightLimit, personLimit, isOnMission, maxSpeed, rangeOfTravel);
        this.length = length;
    }

    public double getLength() { return this.length; }
    public void setLength(double length) { this.length = length; }
}
