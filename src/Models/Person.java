package Models;

import java.util.Date;

public abstract class Person {
    public enum Nation { PL, DE, ENG };

    private String pesel;
    private String name;
    private String surname;
    private Date dob;
    private Nation nationality;

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
}