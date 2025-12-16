package models;

import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
    private List<Floor> floorList;

    @NotNull
    @JsonDeserialize(as = ArrayList.class)
    private List<Doctor> doctors;

    @NotNull
    @JsonDeserialize(as = ArrayList.class)
    private List<Nurse> nurses;

    public Department() {
        this.floorList = new ArrayList<>();
        this.doctors = new ArrayList<>();
        this.nurses = new ArrayList<>();
    }
    public Department(String id, String name) {
        this.id = id;
        this.name = name;
        this.floorList = new ArrayList<>();
        this.doctors = new ArrayList<>();
        this.nurses = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Floor> getFloorList() { return floorList; }
    public List<Doctor> getDoctors() { return doctors; }
    public List<Nurse> getNurses() { return nurses; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setFloorList(List<Floor> floorList) { this.floorList = floorList; }
    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }
    public void setNurses(List<Nurse> nurses) { this.nurses = nurses; }

    public void addDoctor(Doctor doctor) {
        if (doctor != null && !doctors.contains(doctor)) {
            doctors.add(doctor);
            if (doctor.getDepartment() != this) doctor.setDepartment(this);
        }
    }

    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }

    public void addNurse(Nurse nurse) {
        if (nurse != null && !nurses.contains(nurse)) {
            nurses.add(nurse);
        }
    }

    public void removeNurse(Nurse nurse) {
        nurses.remove(nurse);
    }

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
        for (int i = 0; i < floorList.size(); ++i) floorList.get(i).setDepartment(null);
        floorList.clear();
        doctors.clear();
        nurses.clear();
    }
}
