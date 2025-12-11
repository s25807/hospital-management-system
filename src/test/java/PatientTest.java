import annotations.SkipSetup;
import constants.PathConstants;
import exceptions.InvalidPasswordException;
import models.Patient;
import models.Person;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {
    private final ObjectStore objectStore = new ObjectStore();
    private Patient patient;

    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        patient = new Patient(
                "45155151519",
                "username",
                "Qwerty7/",
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
        assertEquals("45155151519", patient.getPesel());
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
    @SkipSetup
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
    void testErrors() {
        assertThrows(NullPointerException.class, () -> {
            ValidatorService.validate(new Patient());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ValidatorService.validate(
                    new Patient("00011100011",
                            "username",
                            "pass",
                            "Luffy",
                            "Monkey",
                            Date.valueOf("2002-05-05"),
                            Person.Nation.PL,
                            Patient.BloodType.A,
                            false,
                            65,
                            170,
                            false));
        });
        assertThrows(InvalidPasswordException.class, () -> {
            ValidatorService.validate(
                    new Patient("00011100011",
                            "username",
                            "password",
                            "Luffy",
                            "Monkey",
                            Date.valueOf("2002-05-05"),
                            Person.Nation.PL,
                            Patient.BloodType.A,
                            false,
                            65,
                            170,
                            false));
        });
    }

    @Test
    void testCalculateAge() {
        assertEquals(24, patient.calculateAge());

        Patient temp = new Patient();
        temp.setDob(Date.valueOf("2002-12-05"));
        assertEquals(23, temp.calculateAge());
    }

    @Test
    void testSerialization() {
        String path = PathConstants.PATIENTS_TESTS + "test-patient.json";

        objectStore.save(patient, path);
        Patient loaded = objectStore.load(Patient.class, path);

        assertEquals("45155151519", loaded.getPesel());
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
}
