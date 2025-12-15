package models;

import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationRoom extends Room{

    @NotNull
    Map<String, Integer> surgicalEquipment;

    @NotNull
    @NotEmpty
    @JsonDeserialize(as = ArrayList.class)
    private List<Operation> operations = new ArrayList<>();

    public OperationRoom() {}
    public OperationRoom(String roomNumber, int maxPeopleAllowed, int occupancy, Floor floor) {
        super(roomNumber, maxPeopleAllowed, occupancy, floor);
        surgicalEquipment = new HashMap<>();
    }
    public OperationRoom(String roomNumber, int maxPeopleAllowed, int occupancy, Floor floor, Map<String, Integer> surgicalEquipment) {
        super(roomNumber, maxPeopleAllowed, occupancy, floor);
        this.surgicalEquipment = surgicalEquipment;
    }

    public Map<String, Integer> getSurgicalEquipment() { return this.surgicalEquipment; }
    public List<Operation> getOperations() { return operations; }

    public void setSurgicalEquipment(Map<String, Integer> surgicalEquipment) { this.surgicalEquipment = surgicalEquipment; }

    public void setOperations(List<Operation> operations) {
        if (operations == null || operations.isEmpty())
            throw new IllegalArgumentException("operations cannot be empty");
        this.operations = new ArrayList<>(operations);
    }

    @JsonIgnore
    public void addItem(String item, int quantity) {
        surgicalEquipment.put(item, quantity);
    }

    @Override
    public void destroyRoom() {
        super.destroyRoom();
        //TODO: Remember to destroy associations
    }
}