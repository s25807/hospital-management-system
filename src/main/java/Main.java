import constants.PathConstants;
import models.Person;
import models.Patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

class Main{
    public static final String mainPatient = PathConstants.PATIENTS_MAIN;

    public static void main(String[] args){
        ObjectMapper mapper = new ObjectMapper();

        Patient patient = new Patient("51563666444", "superman", "abcdefghijk", "John", "Kowalski", Date.valueOf("2002-05-02"), Person.Nation.PL, Patient.BloodType.B, true, 80, 185, true);

        try {
            ValidatorService.validate(patient);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(mainPatient + "patient.json"), patient);
            Patient loaded = mapper.readValue(new File(mainPatient + "patient.json"), Patient.class);
            System.out.println(loaded.getName() + " " + loaded.getSurname());
        }
        catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }
}