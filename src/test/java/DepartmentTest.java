import annotations.SkipSetup;
import constants.PathConstants;
import models.Department;
import models.Doctor;
import models.Employee;
import models.Floor;
import models.MedicalLicense;
import models.Nurse;
import models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;
import validators.ValidatorService;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentTest {

    private final ObjectStore objectStore = new ObjectStore();
    private Department department;
    private Floor floor;

    @BeforeEach
    void setup(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;

        department = new Department("14", "Department of Neurology");
        floor = new Floor(1, 15, department);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("14", department.getId());
        assertEquals("Department of Neurology", department.getName());
        assertEquals(1, department.getFloorList().size());
        assertTrue(department.getFloorList().contains(floor));
    }

    @Test
    void testSetters() {
        department.setId("20");
        department.setName("Department of Cardiology");

        assertEquals("20", department.getId());
        assertEquals("Department of Cardiology", department.getName());
    }

    @Test
    @SkipSetup
    void testDefaultConstructor() {
        Department empty = new Department();

        assertNull(empty.getId());
        assertNull(empty.getName());
        assertNotNull(empty.getFloorList());
        assertTrue(empty.getFloorList().isEmpty());
    }

    @Test
    void testAddAndRemoveFloor() {
        Floor secondFloor = new Floor(2, 10, null);

        department.addFloor(secondFloor);

        assertEquals(2, department.getFloorList().size());
        assertEquals(department, secondFloor.getDepartment());

        department.removeFloor(secondFloor);

        assertEquals(1, department.getFloorList().size());
        assertNull(secondFloor.getDepartment());
    }

    @Test
    void testDestroyDepartment() {
        department.destroyDepartment();

        assertTrue(department.getFloorList().isEmpty());
        assertNull(floor.getDepartment());
    }

    @Test
    @SkipSetup
    void testValidationErrors() {
        assertThrows(NullPointerException.class, () ->
                ValidatorService.validate(new Department())
        );
        assertThrows(NullPointerException.class, () ->
                ValidatorService.validate(new Department(null, "Neurology"))
        );
        assertThrows(IllegalArgumentException.class, () ->
                ValidatorService.validate(new Department("14", ""))
        );
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
        department.addDoctor(doctor1);
        department.addDoctor(doctor2);

        assertEquals(2, department.getDoctors().size());
        assertTrue(department.getDoctors().contains(doctor1));
        assertTrue(department.getDoctors().contains(doctor2));

        // Test removing doctor
        department.removeDoctor(doctor1);
        assertEquals(1, department.getDoctors().size());
        assertFalse(department.getDoctors().contains(doctor1));
        assertTrue(department.getDoctors().contains(doctor2));
    }

    @Test
    void testNurseManagement() {
        // Create test nurses
        MedicalLicense license = new MedicalLicense("LIC001", java.sql.Date.valueOf("2020-01-01"), java.sql.Date.valueOf("2025-01-01"));
        Nurse nurse1 = new Nurse("11111111111", "nurse1", "password123", "Anna", "Smith",
                java.sql.Date.valueOf("1990-03-10"), Person.Nation.DE, "NUR001",
                Employee.Status.ACTIVE, true, license);
        Nurse nurse2 = new Nurse("22222222222", "nurse2", "password123", "Maria", "Johnson",
                java.sql.Date.valueOf("1988-01-01"), Person.Nation.PL, "NUR002",
                Employee.Status.ACTIVE, true, license);

        // Test adding nurses
        department.addNurse(nurse1);
        department.addNurse(nurse2);

        assertEquals(2, department.getNurses().size());
        assertTrue(department.getNurses().contains(nurse1));
        assertTrue(department.getNurses().contains(nurse2));

        // Test removing nurse
        department.removeNurse(nurse1);
        assertEquals(1, department.getNurses().size());
        assertFalse(department.getNurses().contains(nurse1));
        assertTrue(department.getNurses().contains(nurse2));
    }

    @Test
    void testSerialization() {
        String path = PathConstants.DEPARTMENTS_TESTS + "test-department.json";

        objectStore.save(department, path);
        Department loaded = objectStore.load(Department.class, path);

        assertEquals("14", loaded.getId());
        assertEquals("Department of Neurology", loaded.getName());
        assertEquals(1, loaded.getFloorList().size());
        assertEquals(floor.getNumber(), loaded.getFloorList().get(0).getNumber());
    }
}
