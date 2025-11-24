package models;

import annotations.Min;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
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
