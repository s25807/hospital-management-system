package models;

import annotations.NotNull;

public class PatientRoom extends Room{
    @NotNull
    private boolean vip;
    public static final double vipPrice = 1000;

    public PatientRoom() {}
    public PatientRoom(String roomNumber, int maxPeopleAllowed, int occupancy,  boolean vip, Floor floor) {
        super(roomNumber, maxPeopleAllowed, occupancy, floor);
        this.vip = vip;
    }

    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }

    @Override
    public void destroyRoom() {
        super.destroyRoom();
        //TODO: Remember to destroy here associations
    }
}
