package models;

import annotations.NotNull;

import java.util.Map;

public class Van extends AmbulanceVehicle{
    public enum Capability {Low, Medium, High, Extreme}

    @NotNull
    private Capability offRoadCapability;

    public Van() {}
    public Van(String registrationPlate, Brand brand, double weightLimit, int personLimit, boolean isOnMission, double maxSpeed, double rangeOfTravel, Boolean hasDefibrillator, Boolean hasOxygenSupply, Map<String, Integer> instruments, Map<String, Integer> anesthesiaEquipment, Capability offRoadCapability) {
        super(registrationPlate, brand, weightLimit, personLimit, isOnMission, maxSpeed, rangeOfTravel, hasDefibrillator, hasOxygenSupply, instruments, anesthesiaEquipment);
        this.offRoadCapability = offRoadCapability;
    }

    public void setOffRoadCapability(Capability offRoadCapability) { this.offRoadCapability = offRoadCapability; }
    public Capability getOffRoadCapability() { return offRoadCapability; }
}
