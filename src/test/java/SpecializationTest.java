import annotations.SkipSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.Doctor;
import models.Employee;
import models.Floor;
import models.MedicalLicense;
import models.Person;
import models.Specialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpecializationTest {
    private final ObjectStore objectStore = new ObjectStore();
    private Specialization specialization;

    @BeforeEach
    void setup(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        specialization = new Specialization("Neurology",
                new ArrayList<>(List.of("medical degree", "proficiency with MRI", "hospital internship")));
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Neurology", specialization.getName());
        assertArrayEquals(new String[]{"medical degree", "proficiency with MRI", "hospital internship"},
                specialization.getRequirements().toArray());
    }

    @Test
    void testSetters() {
        specialization.setName("Orthopedics");
        assertEquals("Orthopedics", specialization.getName());

        specialization.setRequirements(new ArrayList<>(List.of("stroke management training")));
        assertArrayEquals(new String[]{"stroke management training"},
                specialization.getRequirements().toArray());
    }

    @Test
    @SkipSetup
    void testDefaultConstructor() {
        Specialization emptySpecialization = new Specialization();
        assertNull(emptySpecialization.getName());
        assertNull(emptySpecialization.getRequirements());
        assertNotNull(emptySpecialization.getDoctors());
        assertTrue(emptySpecialization.getDoctors().isEmpty());
    }

    @Test
    void testErrors() {

        assertThrows(NullPointerException.class, () -> {
            Specialization s = new Specialization(
                    null,
                    new ArrayList<>(List.of("medical degree"))
            );
            ValidatorService.validate(s);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Specialization s = new Specialization(
                    "",
                    new ArrayList<>(List.of("medical degree"))
            );
            ValidatorService.validate(s);
        });

        assertThrows(NullPointerException.class, () -> {
            Specialization s = new Specialization(
                    "Neurology",
                    null
            );
            ValidatorService.validate(s);
        });

        ArrayList<String> listWithNull = new ArrayList<>();
        listWithNull.add("degree");
        listWithNull.add(null);
        Specialization specializationWithNullRequirement = new Specialization(
                "Neurology", listWithNull);
        assertDoesNotThrow(() -> ValidatorService.validate(specializationWithNullRequirement));

        ArrayList<String> listWithEmpty = new ArrayList<>();
        listWithEmpty.add("degree");
        listWithEmpty.add("");
        Specialization specializationWithEmptyRequirement = new Specialization(
                "Neurology", listWithEmpty);
        assertDoesNotThrow(() -> ValidatorService.validate(specializationWithEmptyRequirement));
    }

    @Test
    void testDoctorManagement() {
        // Create test doctors
        MedicalLicense license = new MedicalLicense("LIC001", java.sql.Date.valueOf("2020-01-01"), java.sql.Date.valueOf("2025-01-01"));
        Doctor doctor1 = new Doctor("11111111111", "doctor1", "password123", "John", "Doe",
                java.sql.Date.valueOf("1985-05-15"), Person.Nation.PL, "DOC001",
                Employee.Status.ACTIVE, true, true, license);
        Doctor doctor2 = new Doctor("22222222222", "doctor2", "password123", "Jane", "Smith",
                java.sql.Date.valueOf("1980-08-20"), Person.Nation.ENG, "DOC002",
                Employee.Status.ACTIVE, false, false, license);

        // Test adding doctors
        specialization.addDoctor(doctor1);
        specialization.addDoctor(doctor2);

        assertEquals(2, specialization.getDoctors().size());
        assertTrue(specialization.getDoctors().contains(doctor1));
        assertTrue(specialization.getDoctors().contains(doctor2));

        // Test removing doctor
        specialization.removeDoctor(doctor1);
        assertEquals(1, specialization.getDoctors().size());
        assertFalse(specialization.getDoctors().contains(doctor1));
        assertTrue(specialization.getDoctors().contains(doctor2));
    }

    @Test
    void testSerialization() {
        String path = PathConstants.SPECIALIZATIONS_TESTS + "test-specialization.json";
        objectStore.save(specialization, path);

        Specialization loaded = objectStore.load(Specialization.class, path);
        assertEquals("Neurology", loaded.getName());
        assertArrayEquals(
                new String[]{"medical degree", "proficiency with MRI", "hospital internship"},
                loaded.getRequirements().toArray()
        );
    }

}
