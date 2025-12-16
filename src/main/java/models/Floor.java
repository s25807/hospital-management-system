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

    private Department department;

    @NotNull
    @NotEmpty
    private List<Room> roomList;

    public Floor() { this.roomList = new ArrayList<>(); }

    public Floor(int number, int amountOfRooms, Department department) {
        this.number = number;
        this.amountOfRooms = amountOfRooms;
        this.roomList = new ArrayList<>();
        setDepartment(department);
    }

    public int getNumber() { return number; }
    public int getAmountOfRooms() { return amountOfRooms; }
    public Department getDepartment() { return department; }
    public List<Room> getRoomList() { return roomList; }

    public void setNumber(int number) { this.number = number; }
    public void setAmountOfRooms(int amountOfRooms) { this.amountOfRooms = amountOfRooms; }
    public void setDepartment(Department newDepartment) {
        if (this.department == newDepartment) return;

        if (this.department != null) {
            Department oldDepartment = this.department;
            this.department = null;
            oldDepartment.removeFloor(this);
        }

        this.department = newDepartment;

        if (newDepartment != null && !newDepartment.getFloorList().contains(this)) {
            newDepartment.addFloor(this);
        }
    }
    public void setRoomList(List<Room> roomList) { this.roomList = roomList; }
    public void destroyFloor() {
        for (Room room : roomList) room.destroyRoom();

        roomList.clear();

        if (department != null) {
            Department oldDepartment = department;
            department = null;
            oldDepartment.getFloorList().remove(this);
        }
    }
}
