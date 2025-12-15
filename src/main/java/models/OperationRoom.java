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
    @JsonDeserialize(as = ArrayList.class)
    private List<Operation> operations;

    public OperationRoom() { operations = new ArrayList<>(); }
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

    public Map<String, Integer> getSurgicalEquipment() { return this.surgicalEquipment; }
    public List<Operation> getOperations() { return operations; }

    public void setSurgicalEquipment(Map<String, Integer> surgicalEquipment) { this.surgicalEquipment = surgicalEquipment; }
    public void setOperations(List<Operation> operations) { this.operations = new ArrayList<>(operations); }

    public void addOperation(Operation operation) {
        if (operation != null && !hasOperation(operation)) {
            operations.add(operation);
            if (operation.getOperationRoom() != this) operation.setOperationRoom(this);
        }
    }

    public void removeOperation(Operation operation) {
        if (operation != null && hasOperation(operation)) {
            operations.remove(operation);
            if (operation.getOperationRoom() != this) operation.removeOperationRoom(this);
        }
    }

    public boolean hasOperation(Operation operation) { return operations.contains(operation); }

    @JsonIgnore
    public void addItem(String item, int quantity) {
        surgicalEquipment.put(item, quantity);
    }

    @Override
    public void destroyRoom() {
        super.destroyRoom();
        for (int i = 0; i < operations.size(); i++) operations.get(i).removeOperationRoom(this);
    }
}