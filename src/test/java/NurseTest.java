import annotations.SkipSetup;
import constants.PathConstants;
import exceptions.InvalidPasswordException;
import models.Department;
import models.Employee;
import models.MedicalLicense;
import models.Nurse;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NurseTest {
    private final ObjectStore objectStore = new ObjectStore();
    private Nurse nurse;
    private MedicalLicense medicalLicense;

    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        medicalLicense = new MedicalLicense("AAC-DAE-20A", Date.valueOf("2000-10-10"), Date.valueOf("2020-10-10"));

        nurse = new Nurse(
                "22222222222",
                "nurse2",
                "Qwerty7/",
                "Maria",
                "Woźniak",
                Date.valueOf("1988-01-01"),
                Person.Nation.PL,
                "nur_2",
                Nurse.Status.ON_LEAVE,
                false,
                medicalLicense
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("22222222222", nurse.getPesel());
        assertEquals("nurse2", nurse.getUsername());
        assertEquals("Qwerty7/", nurse.getPassword());
        assertEquals("Maria", nurse.getName());
        assertEquals("Woźniak", nurse.getSurname());
        assertEquals(Date.valueOf("1988-01-01"), nurse.getDob());
        assertEquals(Person.Nation.PL, nurse.getNationality());

        assertEquals("nur_2", nurse.getEmployeeId());
        assertEquals(Nurse.Status.ON_LEAVE, nurse.getStatus());
        assertFalse(nurse.isOnDuty());
        assertEquals(Map.of(medicalLicense.getLicenseNumber(), medicalLicense), nurse.getMapOfMedLicenceNumbers());
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
        nurse.registerNewMedLicence(medicalLicense);
        assertTrue(nurse.getMapOfMedLicenceNumbers().containsValue(medicalLicense));

        assertTrue(nurse.findMedicalLicenseNumber("AAC-DAE-20A").isPresent());
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

        assertThrows(NullPointerException.class, () -> { empty.getMapOfMedLicenceNumbers(); });
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
                                true,
                                medicalLicense
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
                                true,
                                medicalLicense
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
    void testDepartmentAssociation() {
        // Test setting department
        Department cardioDept = new Department("DEPT001", "Cardiology");
        Department neuroDept = new Department("DEPT002", "Neurology");

        nurse.setDepartment(cardioDept);
        assertEquals(cardioDept, nurse.getDepartment());
        assertEquals("DEPT001", nurse.getDepartment().getId());
        assertEquals("Cardiology", nurse.getDepartment().getName());

        // Test changing department
        nurse.setDepartment(neuroDept);
        assertEquals(neuroDept, nurse.getDepartment());
        assertEquals("DEPT002", nurse.getDepartment().getId());
        assertEquals("Neurology", nurse.getDepartment().getName());

        // Test removing department (setting to null)
        nurse.setDepartment(null);
        assertNull(nurse.getDepartment());
    }

    @Test
    void testSerialization() {
        String path = PathConstants.NURSES_TESTS + "test-nurse.json";
        objectStore.save(nurse, path);

        Nurse loaded = objectStore.load(Nurse.class, path);
        assertEquals("22222222222", loaded.getPesel());
        assertEquals("Maria", loaded.getName());
        assertEquals("Woźniak", loaded.getSurname());
        assertEquals(Date.valueOf("1988-01-01"), loaded.getDob());
        assertEquals(Person.Nation.PL, loaded.getNationality());
        assertEquals("nur_2", loaded.getEmployeeId());
        assertEquals(Nurse.Status.ON_LEAVE, loaded.getStatus());
        assertFalse(loaded.isOnDuty());
    }
}
