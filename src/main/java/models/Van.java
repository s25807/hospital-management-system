package models;

import annotations.NotNull;

public class Van extends AmbulanceVehicle{
    public enum Capability {Low, Medium, High, Extreme}

    @NotNull
    private Capability offRoadCapability;

    public Van() {}
    public Van(String registrationPlate, Brand brand, double weightLimit, int personLimit, boolean isOnMission, double maxSpeed, double rangeOfTravel, Capability offRoadCapability) {
        super(registrationPlate, brand, weightLimit, personLimit, isOnMission, maxSpeed, rangeOfTravel);
        this.offRoadCapability = offRoadCapability;
    }

    public void setOffRoadCapability(Capability offRoadCapability) { this.offRoadCapability = offRoadCapability; }
    public Capability getOffRoadCapability() { return offRoadCapability; }
}
