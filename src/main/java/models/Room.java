package models;

import annotations.Length;
import annotations.Min;
import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "roomNumber"
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PatientRoom.class, name = "patientRoom"),
        @JsonSubTypes.Type(value = OperationRoom.class, name = "operationRoom"),
        @JsonSubTypes.Type(value = EmergencyRoom.class, name = "emergencyRoom")
})
public abstract class Room {
    @NotNull
    @NotEmpty
    private String roomNumber;

    @Min
    @NotNull
    private int maxPeopleAllowed;

    @NotNull
    private int occupancy;

    @NotNull
    private boolean isFilled;

    @NotNull
    private Floor floor;

    public Room() {}
    public Room(String roomNumber, int maxPeopleAllowed, int occupancy, Floor floor) {
        this.roomNumber = roomNumber;
        this.maxPeopleAllowed = maxPeopleAllowed;
        this.occupancy = occupancy;
        this.floor = floor;
        this.floor.getRoomList().add(this);
        checkIsFilled();
    }

    public String getRoomNumber() { return roomNumber; }
    public int getMaxPeopleAllowed() { return maxPeopleAllowed; }
    public int getOccupancy() { return occupancy; }
    public Floor getFloor() { return floor; }

    @JsonIgnore
    public boolean isFilled() { return isFilled; }

    @JsonIgnore
    public int getRemainingPlaces() { return maxPeopleAllowed - occupancy; }

    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setMaxPeopleAllowed(int maxPeopleAllowed) {  this.maxPeopleAllowed = maxPeopleAllowed; }
    public void setOccupancy(int occupancy) {
        if (occupancy > 0 && occupancy <= maxPeopleAllowed) {
            this.occupancy = occupancy;
            checkIsFilled();
        }
        else {
            throw new IllegalArgumentException("Incorrect occupancy");
        }
    }
    public void setFloor(Floor floor) { this.floor = floor; }

    private void checkIsFilled() { isFilled = getRemainingPlaces() == 0; }

    public void destroyRoom() {
        floor = null;
    }
}
