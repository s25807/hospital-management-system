package models;

import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

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
    private ArrayList<String> requirements;

    public Specialization() {}

    public Specialization(String name, ArrayList<String> requirements) {
        this.name = name;
        this.requirements = requirements;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public ArrayList<String> getRequirements() { return requirements; }

    public void setRequirements(ArrayList<String> requirements) { this.requirements = requirements; }
}
