package models;

import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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

    public Specialization() {}

    public Specialization(String name, List<String> requirements) {
        this.name = name;
        this.requirements = requirements;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<String> getRequirements() { return requirements; }

    public void setRequirements(List<String> requirements) { this.requirements = requirements; }
}
