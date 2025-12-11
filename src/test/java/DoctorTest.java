import annotations.SkipSetup;
import constants.PathConstants;
import exceptions.InvalidPasswordException;
import models.Doctor;
import models.Employee;
import models.MedicalLicense;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorTest {

    private final ObjectStore objectStore = new ObjectStore();
    private Doctor doctor;
    private MedicalLicense medicalLicense;

    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        medicalLicense = new MedicalLicense("AAC-DAE-20A", Date.valueOf("2000-10-10"), Date.valueOf("2020-10-10"));
        doctor = new Doctor(
                "45645645645",
                "doctor2",
                "Qwerty7/",
                "Mark",
                "Smith",
                Date.valueOf("1975-07-20"),
                Person.Nation.ENG,
                "doc_2",
                Doctor.Status.ON_LEAVE,
                false,
                false,
                medicalLicense
        );
    }

    @Test
    void testConstructorAndGetters() {

        assertEquals("45645645645", doctor.getPesel());
        assertEquals("doctor2", doctor.getUsername());
        assertEquals("Qwerty7/", doctor.getPassword());
        assertEquals("Mark", doctor.getName());
        assertEquals("Smith", doctor.getSurname());
        assertEquals(Date.valueOf("1975-07-20"), doctor.getDob());
        assertEquals(Person.Nation.ENG, doctor.getNationality());

        assertEquals("doc_2", doctor.getEmployeeId());
        assertEquals(Doctor.Status.ON_LEAVE, doctor.getStatus());
        assertFalse(doctor.isOnDuty());
        assertFalse(doctor.isHasHeadRole());

        assertEquals(Map.of(medicalLicense.getLicenseNumber(), medicalLicense), doctor.getMapOfMedLicenceNumbers());
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

        assertThrows(NullPointerException.class, () -> { empty.getMapOfMedLicenceNumbers(); });
    }

    @Test
    void testMedicalLicenceOperations() {
        doctor.registerNewMedLicence(medicalLicense);
        assertEquals(medicalLicense, doctor.getMapOfMedLicenceNumbers().get(medicalLicense.getLicenseNumber()));

        assertTrue(doctor.findMedicalLicenseNumber("AAC-DAE-20A").isPresent());
        assertTrue(doctor.findMedicalLicenseNumber("licence4").isEmpty());
    }

    @Test
    void testSupervisor() {
        assertThrows(IllegalArgumentException.class, () -> { doctor.setSupervisor(doctor); });

        MedicalLicense superMedicalLicense = new MedicalLicense("BBB-DAE-20A", Date.valueOf("2000-10-10"), Date.valueOf("2020-10-10"));
        Doctor superDoctor = new Doctor(
                "626272167",
                "supervisor",
                "password123",
                "Marcus",
                "Smith",
                Date.valueOf("1975-07-20"),
                Person.Nation.ENG,
                "doc_2",
                Doctor.Status.ACTIVE,
                false,
                true,
                superMedicalLicense
        );

        doctor.setSupervisor(superDoctor);
        assertEquals(superDoctor, doctor.getSupervisor());
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
                            true,
                            medicalLicense
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
                            true,
                            medicalLicense
                    )
            );
        });
    }

    @Test
    void testSerialization() {
        String path = PathConstants.DOCTORS_TESTS + "test-doctor.json";
        objectStore.save(doctor, path);

        Doctor loaded = objectStore.load(Doctor.class, path);

        assertEquals("45645645645", loaded.getPesel());
        assertEquals("Mark", loaded.getName());
        assertEquals("Smith", loaded.getSurname());
        assertEquals(Date.valueOf("1975-07-20"), loaded.getDob());
        assertEquals(Person.Nation.ENG, loaded.getNationality());
        assertEquals("doc_2", loaded.getEmployeeId());
        assertEquals(Doctor.Status.ON_LEAVE, loaded.getStatus());
        assertFalse(loaded.isOnDuty());
        assertFalse(loaded.isHasHeadRole());
        assertNull(loaded.getSupervisor());
    }
}
