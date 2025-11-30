import annotations.SkipSetup;
import constants.PathConstants;
import exceptions.InvalidPasswordException;
import models.Doctor;
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

public class DoctorTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private Doctor doctor;
    private Doctor doctorWithLicences;

    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        doctor = new Doctor(
                "12312312312",
                "doctor1",
                "newPassword1",
                "Jan",
                "Nowak",
                Date.valueOf("1980-01-15"),
                Person.Nation.PL,
                "doc_1",
                Doctor.Status.ACTIVE,
                true,
                true
        );

        doctorWithLicences = new Doctor(
                "45645645645",
                "doctor2",
                "password123",
                "Mark",
                "Smith",
                Date.valueOf("1975-07-20"),
                Person.Nation.ENG,
                "doc_2",
                Doctor.Status.ON_LEAVE,
                false,
                List.of("licence1", "licence2"),
                false
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("12312312312", doctor.getPesel());
        assertEquals("Jan", doctor.getName());
        assertEquals("Nowak", doctor.getSurname());
        assertEquals(Date.valueOf("1980-01-15"), doctor.getDob());
        assertEquals(Person.Nation.PL, doctor.getNationality());

        assertEquals("doc_1", doctor.getEmployeeId());
        assertEquals(Doctor.Status.ACTIVE, doctor.getStatus());
        assertTrue(doctor.isOnDuty());
        assertTrue(doctor.isHasHeadRole());

        assertEquals("45645645645", doctorWithLicences.getPesel());
        assertEquals("doctor2", doctorWithLicences.getUsername());
        assertEquals("password123", doctorWithLicences.getPassword());
        assertEquals("Mark", doctorWithLicences.getName());
        assertEquals("Smith", doctorWithLicences.getSurname());
        assertEquals(Date.valueOf("1975-07-20"), doctorWithLicences.getDob());
        assertEquals(Person.Nation.ENG, doctorWithLicences.getNationality());

        assertEquals("doc_2", doctorWithLicences.getEmployeeId());
        assertEquals(Doctor.Status.ON_LEAVE, doctorWithLicences.getStatus());
        assertFalse(doctorWithLicences.isOnDuty());
        assertFalse(doctorWithLicences.isHasHeadRole());

        assertEquals(List.of("licence1", "licence2"), doctorWithLicences.getListOfMedLicenceNumbers());
    }

    @Test
    void testSetters() {
        doctor.assignHeadRole(false);
        assertFalse(doctor.isHasHeadRole());

        doctor.setStatus(Doctor.Status.ON_LEAVE);
        assertEquals(Doctor.Status.ON_LEAVE, doctor.getStatus());

        doctor.toggleDuty(false);
        assertFalse(doctor.isOnDuty());

        doctor.setEmployeeId("doc_100");
        assertEquals("doc_100", doctor.getEmployeeId());

        doctor.setName("Krzysztof");
        assertEquals("Krzysztof", doctor.getName());

        doctor.setNationality(Person.Nation.DE);
        assertEquals(Person.Nation.DE, doctor.getNationality());
    }

    @Test
    @SkipSetup
    void testDefaultConstructor() {
        Doctor empty = new Doctor();
        assertNull(empty.getPesel());
        assertNull(empty.getName());
        assertNull(empty.getSurname());
        assertNull(empty.getDob());
        assertNull(empty.getNationality());
        assertNull(empty.getEmployeeId());
        assertNull(empty.getStatus());
        assertFalse(empty.isOnDuty());
        assertFalse(empty.isHasHeadRole());

        assertNull(empty.getListOfMedLicenceNumbers());
    }

    @Test
    void testMedicalLicenceOperations() {
        doctor.registerNewMedLicence("licence3");
        assertTrue(doctor.getListOfMedLicenceNumbers().contains("licence3"));

        assertTrue(doctor.findMedicalLicenseNumber("licence3").isPresent());
        assertTrue(doctor.findMedicalLicenseNumber("licence4").isEmpty());
    }

    @Test
    void testErrors() {

        assertThrows(NullPointerException.class, () -> {
            ValidatorService.validate(new Doctor());
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ValidatorService.validate(
                    new Doctor(
                            "12312312312",
                            "username",
                            "pass",
                            "Jan",
                            "Nowak",
                            Date.valueOf("1980-01-15"),
                            Person.Nation.PL,
                            "emp_1",
                            Doctor.Status.ACTIVE,
                            true,
                            true
                    )
            );
        });

        assertThrows(InvalidPasswordException.class, () -> {
            ValidatorService.validate(
                    new Doctor(
                            "12312312312",
                            "username",
                            "password",
                            "Jan",
                            "Nowak",
                            Date.valueOf("1980-01-15"),
                            Person.Nation.PL,
                            "emp_1",
                            Doctor.Status.ACTIVE,
                            true,
                            true
                    )
            );
        });
    }

    @Test
    void testSerialization() {
        String path = PathConstants.DOCTORS_TESTS;

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(path + "test-doctor.json"), doctor);

            Doctor loaded = mapper.readValue(new File(path + "test-doctor.json"), Doctor.class);

            assertEquals("12312312312", loaded.getPesel());
            assertEquals("Jan", loaded.getName());
            assertEquals("Nowak", loaded.getSurname());
            assertEquals(Date.valueOf("1980-01-15"), loaded.getDob());
            assertEquals(Person.Nation.PL, loaded.getNationality());
            assertEquals("doc_1", loaded.getEmployeeId());
            assertEquals(Doctor.Status.ACTIVE, loaded.getStatus());
            assertTrue(loaded.isOnDuty());
            assertTrue(loaded.isHasHeadRole());
        }
        catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }
}
