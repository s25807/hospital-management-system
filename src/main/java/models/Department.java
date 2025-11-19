package models;

import annotations.NotNull;

public class Department {
    @NotNull
    private String id;

    @NotNull
    private String name;

    public Department() {}
    public Department(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
