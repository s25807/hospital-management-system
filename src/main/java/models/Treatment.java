package models;

import annotations.NotEmpty;
import annotations.NotNull;

public abstract class Treatment {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private double dose;


}
