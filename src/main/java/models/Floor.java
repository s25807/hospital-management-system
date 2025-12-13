package models;

import annotations.Min;
import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "number"
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
public class Floor {
    @NotNull
    private int number;

    @Min
    @NotNull
    private int amountOfRooms;

    @NotNull
    @NotEmpty
    private List<Room> roomList;

    public Floor() { this.roomList = new ArrayList<>(); }

    public Floor(int number, int amountOfRooms) {
        this.number = number;
        this.amountOfRooms = amountOfRooms;
        this.roomList = new ArrayList<>();
    }

    public int getNumber() { return number; }
    public int getAmountOfRooms() { return amountOfRooms; }
    public List<Room> getRoomList() { return roomList; }

    public void setNumber(int number) { this.number = number; }
    public void setAmountOfRooms(int amountOfRooms) { this.amountOfRooms = amountOfRooms; }
    public void setRoomList(List<Room> roomList) { this.roomList = roomList; }

    public void destroyFloor() {
        for (Room room : roomList) room.destroyRoom();
    }
}
