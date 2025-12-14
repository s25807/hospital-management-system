package models;

import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
public class Specialization {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private List<String> requirements;

    @NotNull
    @JsonDeserialize(as = ArrayList.class)
    private List<Doctor> doctors;

    public Specialization() {
        this.doctors = new ArrayList<>();
    }

    public Specialization(String name, List<String> requirements) {
        this.name = name;
        this.requirements = requirements;
        this.doctors = new ArrayList<>();
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<String> getRequirements() { return requirements; }

    public void setRequirements(List<String> requirements) { this.requirements = requirements; }

    public List<Doctor> getDoctors() { return doctors; }

    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }

    public void addDoctor(Doctor doctor) {
        if (doctor != null && !doctors.contains(doctor)) {
            doctors.add(doctor);
        }
    }

    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }
}
