package models;

import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class OperationRoom extends Room{

    @NotNull
    Map<String, Integer> surgicalEquipment;

    public OperationRoom() {}
    public OperationRoom(String roomNumber, int maxPeopleAllowed, int occupancy) {
        super(roomNumber, maxPeopleAllowed, occupancy);
        surgicalEquipment = new HashMap<>();
    }
    public OperationRoom(String roomNumber, int maxPeopleAllowed, int occupancy, Map<String, Integer> surgicalEquipment) {
        super(roomNumber, maxPeopleAllowed, occupancy);
        this.surgicalEquipment = surgicalEquipment;
    }

    public void setSurgicalEquipment(Map<String, Integer> surgicalEquipment) { this.surgicalEquipment = surgicalEquipment; }
    public Map<String, Integer> getSurgicalEquipment() { return this.surgicalEquipment; }

    @JsonIgnore
    public void addItem(String item, int quantity) {
        surgicalEquipment.put(item, quantity);
    }
}
