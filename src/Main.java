import Models.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import Models.Patient;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

class Main{
    public static void main(String[] args){
        ObjectMapper mapper = new ObjectMapper();

        Patient patient = new  Patient("4515515151", "Jake", "Kowalski", new Date(2001, 5, 5), Person.Nation.PL, Patient.BloodType.A, true, 80, 180, true);

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