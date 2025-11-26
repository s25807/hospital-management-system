package models;

import annotations.NotNull;

public class Van extends AmbulanceVehicle{
    @NotNull
    private boolean offRoadCapability;

    public Van() {}
    public Van(String registrationPlate, Brand brand, double weightLimit, int personLimit, boolean isOnMission, double maxSpeed, double rangeOfTravel, boolean offRoadCapability) {
        super(registrationPlate, brand, weightLimit, personLimit, isOnMission, maxSpeed, rangeOfTravel);
        this.offRoadCapability = offRoadCapability;
    }

    public void setOffRoadCapability(boolean offRoadCapability) { this.offRoadCapability = offRoadCapability; }
    public boolean isOffRoadCapability() { return offRoadCapability; }
}
