package models;

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
    @JsonDeserialize(as = ArrayList.class)
    private List<Operation> operations;

    public OperationRoom() {
        operations = new ArrayList<>();
    }
    public OperationRoom(String roomNumber, int maxPeopleAllowed, int occupancy, Floor floor) {
        super(roomNumber, maxPeopleAllowed, occupancy, floor);
        surgicalEquipment = new HashMap<>();
        operations = new ArrayList<>();
    }
    public OperationRoom(String roomNumber, int maxPeopleAllowed, int occupancy, Floor floor, Map<String, Integer> surgicalEquipment) {
        super(roomNumber, maxPeopleAllowed, occupancy, floor);
        this.surgicalEquipment = surgicalEquipment;
        operations = new ArrayList<>();
    }

    public void setSurgicalEquipment(Map<String, Integer> surgicalEquipment) { this.surgicalEquipment = surgicalEquipment; }
    public Map<String, Integer> getSurgicalEquipment() { return this.surgicalEquipment; }

    public List<Operation> getOperations() { return operations; }
    public void setOperations(List<Operation> operations) { this.operations = operations; }

    public void addOperation(Operation operation) {
        if (operation != null && !operations.contains(operation)) {
            operations.add(operation);
            if (operation.getOperationRoom() != this) {
                operation.setOperationRoom(this);
            }
        }
    }

    public void removeOperation(Operation operation) {
        if (operations.remove(operation)) {
            if (operation.getOperationRoom() == this) {
                operation.setOperationRoom(null);
            }
        }
    }

    @JsonIgnore
    public void addItem(String item, int quantity) {
        surgicalEquipment.put(item, quantity);
    }

    @Override
    public void destroyRoom() {
        super.destroyRoom();
        // Clean up operation associations
        for (Operation operation : operations) {
            if (operation.getOperationRoom() == this) {
                operation.setOperationRoom(null);
            }
        }
        operations.clear();
    }
}
