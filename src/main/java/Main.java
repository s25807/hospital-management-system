import models.Person;
import models.Patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

class Main{
    public static void main(String[] args){
        ObjectMapper mapper = new ObjectMapper();

        Patient patient = new Patient("51563666", "John", "Kowalski", Date.valueOf("2002-05-02"), Person.Nation.PL, Patient.BloodType.B, true, 80, 185, true);

        try {
            ValidatorService.validate(patient);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("data/patient.json"), patient);
            Patient loaded = mapper.readValue(new File("data/patient.json"), Patient.class);
            System.out.println(loaded.getName() + " " + loaded.getSurname());
        }
        catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }
}