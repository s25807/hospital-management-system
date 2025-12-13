import annotations.SkipSetup;
import constants.PathConstants;
import models.Department;
import models.Floor;
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
