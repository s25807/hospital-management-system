package models;

import annotations.NotNull;

import java.util.Map;

public class Boat extends AmbulanceVehicle {
    @NotNull
    private double length;

    public Boat() {}
    public Boat(String registrationPlate, Brand brand, double weightLimit, int personLimit, boolean isOnMission, double maxSpeed, double rangeOfTravel, Boolean hasDefibrillator, Boolean hasOxygenSupply, Map<String, Integer> instruments, Map<String, Integer> anesthesiaEquipment, double length) {
        super(registrationPlate, brand, weightLimit, personLimit, isOnMission, maxSpeed, rangeOfTravel, hasDefibrillator, hasOxygenSupply, instruments, anesthesiaEquipment);
        this.length = length;
    }

    public double getLength() { return this.length; }
    public void setLength(double length) { this.length = length; }
}
