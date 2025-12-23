package models;

import annotations.NotNull;

import java.util.Map;

public class Helicopter extends AmbulanceVehicle{

    @NotNull
    private double maxAltitude;

    public Helicopter() {}
    public Helicopter(String registrationPlate, Brand brand, double weightLimit, int personLimit, boolean isOnMission, double maxSpeed, double rangeOfTravel, Boolean hasDefibrillator, Boolean hasOxygenSupply, Map<String, Integer> instruments, Map<String, Integer> anesthesiaEquipment, double maxAltitude) {
        super(registrationPlate, brand, weightLimit, personLimit, isOnMission, maxSpeed, rangeOfTravel, hasDefibrillator, hasOxygenSupply, instruments, anesthesiaEquipment);
        this.maxAltitude = maxAltitude;
    }

    public double getMaxAltitude() { return maxAltitude; }
    public void setMaxAltitude(double maxAltitude) { this.maxAltitude = maxAltitude; }
}
