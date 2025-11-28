package models;

import annotations.Min;
import annotations.NotEmpty;
import annotations.NotNull;
import annotations.ValidDate;

import java.sql.Date;

public abstract class Treatment {

    @NotNull
    @NotEmpty
    private String name;

    @Min(value = 0.1)
    private double dose;

    @NotNull
    @ValidDate(value = ValidDate.Mode.FUTURE)
    private Date startDate;

    @NotNull
    @ValidDate(value = ValidDate.Mode.FUTURE)
    private Date endDate;

    public Treatment() {}

    public Treatment(String name, double dose, Date startDate, Date endDate) {
        this.name = name;
        this.dose = dose;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() { return name; }
    public double getDose() { return dose; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }

    public void setName(String name) { this.name = name; }
    public void setDose(double dose) { this.dose = dose; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public long calculateTotalTime() {
        long diff = endDate.getTime() - startDate.getTime();
        return diff / (1000L * 60L * 60L * 24L);
    }

}
