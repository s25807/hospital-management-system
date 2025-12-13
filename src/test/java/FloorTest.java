import annotations.SkipSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FloorTest {
    private final ObjectStore objectStore = new ObjectStore();
    private Floor floor;
    private Department department;

    @BeforeEach
    void setup(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        department = new Department("14", "Department of Neurology");
        floor = new Floor(1, 15, department);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1, floor.getNumber());
        assertEquals(15, floor.getAmountOfRooms());
        assertEquals(department, floor.getDepartment());

        assertEquals(1, department.getFloorList().size());
        assertTrue(department.getFloorList().contains(floor));
    }

    @Test
    void testSetters() {
        floor.setNumber(1);
        assertEquals(1, floor.getNumber());

        floor.setAmountOfRooms(10);
        assertEquals(10, floor.getAmountOfRooms());
    }

    @Test
    @SkipSetup
    void testDefaultConstructor() {
        Floor empty = new Floor();

        assertEquals(0, empty.getNumber());
        assertEquals(0, empty.getAmountOfRooms());
        assertNull(empty.getDepartment());
        assertNotNull(empty.getRoomList());
        assertTrue(empty.getRoomList().isEmpty());
    }

    @Test
    void testDestroyFloorCompositionAndAggregation() {
        PatientRoom r1 = new PatientRoom("201", 2, 1, true, floor);
        PatientRoom r2 = new PatientRoom("202", 2, 2, false, floor);

        if (!floor.getRoomList().contains(r1)) floor.getRoomList().add(r1);
        if (!floor.getRoomList().contains(r2)) floor.getRoomList().add(r2);

        assertEquals(2, floor.getRoomList().size());

        floor.destroyFloor();

        assertNull(r1.getFloor());
        assertNull(r2.getFloor());

        assertTrue(floor.getRoomList().isEmpty());

        assertNull(floor.getDepartment());
        assertFalse(department.getFloorList().contains(floor));
    }

    @Test
    void testDepartmentReassignment() {
        Department newDept = new Department("13", "Oncology");

        floor.setDepartment(newDept);

        assertEquals(newDept, floor.getDepartment());
        assertTrue(newDept.getFloorList().contains(floor));
        assertFalse(department.getFloorList().contains(floor));

        floor.setDepartment(department);
        assertEquals(department, floor.getDepartment());
        assertTrue(department.getFloorList().contains(floor));
        assertFalse(newDept.getFloorList().contains(floor));
    }

    @Test
    void testErrorsValidation() {
        assertThrows(Exception.class, () -> {
            ValidatorService.validate(new Floor());
        });

        Floor invalid = new Floor(1, 0, department);
        assertThrows(Exception.class, () -> {
            ValidatorService.validate(invalid);
        });
    }

    @Test
    void testSerialization() {
        String path = PathConstants.FLOORS_TESTS + "test-floor.json";
        objectStore.save(floor,  path);

        Floor loaded = objectStore.load(Floor.class, path);
        assertEquals(1, loaded.getNumber());
        assertEquals(15, loaded.getAmountOfRooms());
        assertNotNull(loaded.getDepartment());
        assertEquals("14", loaded.getDepartment().getId());
        assertEquals("Department of Neurology", loaded.getDepartment().getName());
    }
}
