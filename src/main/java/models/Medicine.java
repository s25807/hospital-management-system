package models;

import annotations.NotEmpty;
import annotations.NotNull;

import java.sql.Date;

public class Medicine extends Treatment {

    @NotNull
    @NotEmpty
    private String company;

    @NotNull
    @NotEmpty
    private String serialNumber;

    public Medicine() {
        super();
    }

    public Medicine(String name, double dose, Date startDate, Date endDate, String company, String serialNumber) {
        super(name, dose, startDate, endDate);
        this.company = company;
        this.serialNumber = serialNumber;
    }

    public String getCompany() {
        return company;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
