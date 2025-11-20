package models;

import annotations.NotNull;

import java.util.ArrayList;

public class Specialization {
    @NotNull
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
