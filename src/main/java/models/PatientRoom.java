package models;

import annotations.NotNull;

public class PatientRoom extends Room{
    @NotNull
    private boolean vip;
    public static final double vipPrice = 1000;


    public PatientRoom() {}
    public PatientRoom(String roomNumber, int maxPeopleAllowed, int occupancy,  boolean vip) {
        super(roomNumber, maxPeopleAllowed, occupancy);
        this.vip = vip;
    }

    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }
}
