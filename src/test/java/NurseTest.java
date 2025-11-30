import annotations.SkipSetup;
import constants.PathConstants;
import exceptions.InvalidPasswordException;
import models.Nurse;
import models.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NurseTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private Nurse nurse;
    private Nurse nurseWithLicences;

    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        nurse = new Nurse(
                "11111111111",
                "nurse1",
                "password123",
                "Anna",
                "Schmid",
                Date.valueOf("1990-03-10"),
                Person.Nation.DE,
                "nur_1",
                Nurse.Status.ACTIVE,
                true
        );

        nurseWithLicences = new Nurse(
                "22222222222",
                "nurse2",
                "password123",
                "Maria",
                "Woźniak",
                Date.valueOf("1988-01-01"),
                Person.Nation.PL,
                "nur_2",
                Nurse.Status.ON_LEAVE,
                false,
                List.of("LIC100", "LIC200")
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("11111111111", nurse.getPesel());
        assertEquals("nurse1", nurse.getUsername());
        assertEquals("password123", nurse.getPassword());
        assertEquals("Anna", nurse.getName());
        assertEquals("Schmid", nurse.getSurname());
        assertEquals(Date.valueOf("1990-03-10"), nurse.getDob());
        assertEquals(Person.Nation.DE, nurse.getNationality());

        assertEquals("nur_1", nurse.getEmployeeId());
        assertEquals(Nurse.Status.ACTIVE, nurse.getStatus());
        assertTrue(nurse.isOnDuty());
        assertTrue(nurse.getListOfMedLicenceNumbers().isEmpty());


        assertEquals("22222222222", nurseWithLicences.getPesel());
        assertEquals("nurse2", nurseWithLicences.getUsername());
        assertEquals("password123", nurseWithLicences.getPassword());
        assertEquals("Maria", nurseWithLicences.getName());
        assertEquals("Woźniak", nurseWithLicences.getSurname());
        assertEquals(Date.valueOf("1988-01-01"), nurseWithLicences.getDob());
        assertEquals(Person.Nation.PL, nurseWithLicences.getNationality());

        assertEquals("nur_2", nurseWithLicences.getEmployeeId());
        assertEquals(Nurse.Status.ON_LEAVE, nurseWithLicences.getStatus());
        assertFalse(nurseWithLicences.isOnDuty());
        assertEquals(List.of("LIC100", "LIC200"), nurseWithLicences.getListOfMedLicenceNumbers());
    }

    @Test
    void testSetters() {
        nurse.setStatus(Nurse.Status.INACTIVE);
        assertEquals(Nurse.Status.INACTIVE, nurse.getStatus());

        nurse.toggleDuty(false);
        assertFalse(nurse.isOnDuty());

        nurse.setName("Eva");
        assertEquals("Eva", nurse.getName());

        nurse.setNationality(Person.Nation.ENG);
        assertEquals(Person.Nation.ENG, nurse.getNationality());
    }

    @Test
    void testMedicalLicenceOperations() {
        nurse.registerNewMedLicence("MED123");
        assertTrue(nurse.getListOfMedLicenceNumbers().contains("MED123"));

        assertTrue(nurse.findMedicalLicenseNumber("MED123").isPresent());
        assertTrue(nurse.findMedicalLicenseNumber("med1").isEmpty());
    }

    @Test
    @SkipSetup
    void testDefaultConstructor() {
        Nurse empty = new Nurse();
        assertNull(empty.getPesel());
        assertNull(empty.getUsername());
        assertNull(empty.getPassword());
        assertNull(empty.getName());
        assertNull(empty.getSurname());
        assertNull(empty.getDob());
        assertNull(empty.getNationality());

        assertNull(empty.getEmployeeId());
        assertNull(empty.getStatus());
        assertFalse(empty.isOnDuty());

        assertNull(empty.getListOfMedLicenceNumbers());
    }

    @Test
    void testErrors() {

        assertThrows(NullPointerException.class, () -> {
            ValidatorService.validate(new Nurse());
        });

        assertThrows(Exception.class, () -> {
            try {
                ValidatorService.validate(
                        new Nurse(
                                "11111111111",
                                "username",
                                "pass",
                                "Anna",
                                "Schmid",
                                Date.valueOf("1990-03-10"),
                                Person.Nation.DE,
                                "nur_1",
                                Nurse.Status.ACTIVE,
                                true
                        )
                );
            } catch (Exception e) {
                assertTrue(
                        e instanceof InvalidPasswordException ||
                                e instanceof IllegalArgumentException
                );
                throw e;
            }
        });

        assertThrows(Exception.class, () -> {
            try {
                ValidatorService.validate(
                        new Nurse(
                                "11111111111",
                                "username",
                                "password",
                                "Anna",
                                "Schmid",
                                Date.valueOf("1990-03-10"),
                                Person.Nation.DE,
                                "nur_1",
                                Nurse.Status.ACTIVE,
                                true
                        )
                );
            } catch (Exception e) {
                assertTrue(
                        e instanceof InvalidPasswordException ||
                                e instanceof IllegalArgumentException
                );
                throw e;
            }
        });
    }


    @Test
    void testSerialization() {
        String path = PathConstants.NURSES_TESTS;

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(path + "test-nurse.json"), nurse);

            Nurse loaded = mapper.readValue(new File(path + "test-nurse.json"), Nurse.class);

            assertEquals("11111111111", loaded.getPesel());
            assertEquals("Anna", loaded.getName());
            assertEquals("Schmid", loaded.getSurname());
            assertEquals(Date.valueOf("1990-03-10"), loaded.getDob());
            assertEquals(Person.Nation.DE, loaded.getNationality());
            assertEquals("nur_1", loaded.getEmployeeId());
            assertEquals(Nurse.Status.ACTIVE, loaded.getStatus());
            assertTrue(loaded.isOnDuty());

        } catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }
}
