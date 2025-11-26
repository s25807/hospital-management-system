package models;

import annotations.NotNull;

public class Helicopter extends AmbulanceVehicle{

    @NotNull
    private double maxAltitude;

    public Helicopter() {}
    public Helicopter(String registrationPlate, Brand brand, double weightLimit, int personLimit, boolean isOnMission, double maxSpeed, double rangeOfTravel, double maxAltitude) {
        super(registrationPlate, brand, weightLimit, personLimit, isOnMission, maxSpeed, rangeOfTravel);
        this.maxAltitude = maxAltitude;
    }

    public double getMaxAltitude() { return maxAltitude; }
    public void setMaxAltitude(double maxAltitude) { this.maxAltitude = maxAltitude; }
}
