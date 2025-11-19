import models.Patient;
import models.Person;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient(
                "4515515151",
                "Jake",
                "Kowalski",
                Date.valueOf("2001-05-05"),
                Person.Nation.PL,
                Patient.BloodType.A,
                true,
                80,
                180,
                true
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("4515515151", patient.getPesel());
        assertEquals("Jake", patient.getName());
        assertEquals("Kowalski", patient.getSurname());
        assertEquals(Date.valueOf("2001-05-05"), patient.getDob());
        assertEquals(Person.Nation.PL, patient.getNationality());

        assertEquals(Patient.BloodType.A, patient.getBloodType());
        assertTrue(patient.getInsurance());
        assertEquals(80.0, patient.getWeight());
        assertEquals(180.0, patient.getHeight());
        assertTrue(patient.getIsActive());
    }

    @Test
    void testSetters() {
        patient.setBloodType(Patient.BloodType.O);
        assertEquals(Patient.BloodType.O, patient.getBloodType());

        patient.setInsurance(false);
        assertFalse(patient.getInsurance());

        patient.setWeight(80.0);
        assertEquals(80.0, patient.getWeight());

        patient.setHeight(185.0);
        assertEquals(185.0, patient.getHeight());

        patient.setIsActive(false);
        assertFalse(patient.getIsActive());
    }

    @Test
    void testDefaultConstructor() {
        Patient emptyPatient = new Patient();
        assertNull(emptyPatient.getPesel());
        assertNull(emptyPatient.getName());
        assertNull(emptyPatient.getSurname());
        assertNull(emptyPatient.getDob());
        assertNull(emptyPatient.getNationality());
        assertNull(emptyPatient.getBloodType());
        assertFalse(emptyPatient.getInsurance());
        assertEquals(0.0, emptyPatient.getWeight());
        assertEquals(0.0, emptyPatient.getHeight());
        assertFalse(emptyPatient.getIsActive());
    }

    @Test
    void testSerialization() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("data/test-patient.json"), patient);
            Patient loaded = mapper.readValue(new File("data/test-patient.json"), Patient.class);
            assertEquals("4515515151", loaded.getPesel());
            assertEquals("Jake", loaded.getName());
            assertEquals("Kowalski", loaded.getSurname());
            assertEquals(Date.valueOf("2001-05-05"), loaded.getDob());
            assertEquals(Person.Nation.PL, loaded.getNationality());

            assertEquals(Patient.BloodType.A, loaded.getBloodType());
            assertTrue(loaded.getInsurance());
            assertEquals(80.0, loaded.getWeight());
            assertEquals(180.0, loaded.getHeight());
            assertTrue(loaded.getIsActive());
        }
        catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }
}
