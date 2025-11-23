package models;

import annotations.Min;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
    private String roomNumber;

    @Min
    @NotNull
    private int maxPeopleAllowed;

    @NotNull
    private int occupancy;

    @NotNull
    private boolean isFilled;

    public Room() {}
    public Room(String roomNumber, int maxPeopleAllowed, int occupancy) {
        this.roomNumber = roomNumber;
        this.maxPeopleAllowed = maxPeopleAllowed;
        this.occupancy = occupancy;
        checkIsFilled();
    }

    public String getRoomNumber() { return roomNumber; }
    public int getMaxPeopleAllowed() { return maxPeopleAllowed; }
    public int getOccupancy() { return occupancy; }

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

    private void checkIsFilled() { isFilled = getRemainingPlaces() == 0; }
}
