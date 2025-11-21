package models;

import java.sql.Date;

import annotations.*;
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

    @Unique
    @NotNull
    private String username;

    @NotNull
    @Password
    @Min(value = 8)
    private String password;

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
    public Person(String pesel, String username, String password, String name, String surname, Date dob, Nation nationality) {
        this.pesel = pesel;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.nationality = nationality;
    }

    public String getPesel() { return pesel; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Date getDob() { return dob; }
    public Nation getNationality() { return nationality; }

    public void setPesel(String pesel) { this.pesel = pesel; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setDob(Date dob) { this.dob = dob; }
    public void setNationality(Nation nationality) { this.nationality = nationality; }
}