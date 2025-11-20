package models;

import annotations.Min;
import annotations.NotNull;

public class Floor {
    @NotNull
    private int number;
    @NotNull
    @Min(value = 1)
    private int amountOfRooms;

    public Floor() {}

    public Floor(int number, int amountOfRooms) {
        this.number = number;
        this.amountOfRooms = amountOfRooms;
    }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public int getAmountOfRooms() { return amountOfRooms; }

    public void setAmountOfRooms(int amountOfRooms) { this.amountOfRooms = amountOfRooms; }
}
