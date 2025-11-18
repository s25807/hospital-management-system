import Models.Patient;
import Models.Person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
