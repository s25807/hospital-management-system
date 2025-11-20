package models;

import java.sql.Date;

import annotations.Length;
import annotations.NotNull;
import annotations.ValidDate;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Patient.class, name = "patient")
})
public abstract class Person {
    public enum Nation { PL, DE, ENG };

    @NotNull
    @Length(value = 11)
    private String pesel;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @ValidDate(value = ValidDate.Mode.PAST)
    private Date dob;

    @NotNull
    private Nation nationality;

    public Person() {}
    public Person(String pesel, String name, String surname, Date dob, Nation nationality) {
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.nationality = nationality;
    }

    public String getPesel() { return pesel; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Date getDob() { return dob; }
    public Nation getNationality() { return nationality; }

    public void setPesel(String pesel) { this.pesel = pesel; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setDob(Date dob) { this.dob = dob; }
    public void setNationality(Nation nationality) { this.nationality = nationality; }

    private void validate() { }
}