package models;

import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
public class Department {
    @NotNull
    @NotEmpty
    private String id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private List<Floor> floorList;

    public Department() { this.floorList = new ArrayList<>(); }
    public Department(String id, String name) {
        this.id = id;
        this.name = name;
        this.floorList = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Floor> getFloorList() { return floorList; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setFloorList(List<Floor> floorList) { this.floorList = floorList; }

    //public void changeFloor() {}

    public void addFloor(Floor floor) {
        if (floor != null && !floorList.contains(floor)) {
            floorList.add(floor);
            if (floor.getDepartment() != this)
                floor.setDepartment(this);
        }
    }

    public void removeFloor(Floor floor) {
        if (floor == null) return;

        if (floorList.remove(floor)) {
            if (floor.getDepartment() == this)
                floor.setDepartment(null);
        }
    }

    public void destroyDepartment() {
        List<Floor> copy = new ArrayList<>(floorList);
        for (Floor floor : copy) {
            floor.setDepartment(null);
        }
        floorList.clear();
    }
}
