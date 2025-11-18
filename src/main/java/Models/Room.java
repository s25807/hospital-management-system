package Models;

public abstract class Room {
    private String roomNumber;
    private int maxPeopleAllowed;
    private int occupancy;
    private boolean isFilled;

    public Room(String roomNumber, int maxPeopleAllowed, int occupancy) {
        this.roomNumber = roomNumber;
        this.maxPeopleAllowed = maxPeopleAllowed;
        this.occupancy = occupancy;
        checkIsFilled();
    }

    public String getRoomNumber() { return roomNumber; }
    public int getMaxPeopleAllowed() { return maxPeopleAllowed; }
    public int getOccupancy() { return occupancy; }
    public boolean isFilled() { return isFilled; }
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

    private void checkIsFilled() {
        int remaining = maxPeopleAllowed - occupancy;
        isFilled = remaining == 0;
    }
}
