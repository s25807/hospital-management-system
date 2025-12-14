import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import annotations.SkipSetup;
import constants.PathConstants;
import models.Department;
import models.Floor;
import models.Operation;
import models.OperationRoom;
import util.ObjectStore;
import validators.ValidatorService;

public class OperationRoomTest {
    private final ObjectStore objectStore = new ObjectStore();
    private OperationRoom operationRoom;
    private Map<String, Integer> equipment;
    private Floor floor;
    private Department department;

    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        department = new Department("14", "Department of Neurology");
        floor = new Floor(1, 15, department);
        equipment = new HashMap<>();
        equipment.put("scalpel", 5);
        equipment.put("clamp", 10);

        operationRoom = new OperationRoom("1S", 3, 2, floor, equipment);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("1S", operationRoom.getRoomNumber());
        assertEquals(3, operationRoom.getMaxPeopleAllowed());
        assertEquals(2, operationRoom.getOccupancy());
        assertFalse(operationRoom.isFilled());
        assertEquals(1, operationRoom.getRemainingPlaces());

        assertEquals(2, operationRoom.getSurgicalEquipment().size());
        assertEquals(5, operationRoom.getSurgicalEquipment().get("scalpel"));
        assertEquals(10, operationRoom.getSurgicalEquipment().get("clamp"));
    }

    @Test
    void testSecondConstructorEmptyEquipment() {
        OperationRoom or = new OperationRoom("2S", 2, 1, floor);
        assertEquals("2S", or.getRoomNumber());
        assertEquals(2, or.getMaxPeopleAllowed());
        assertEquals(1, or.getOccupancy());
        assertNotNull(or.getSurgicalEquipment());
        assertTrue(or.getSurgicalEquipment().isEmpty());
    }

    @Test
    void testSettersAndAddItem() {
        operationRoom.setRoomNumber("OR-3");
        assertEquals("OR-3", operationRoom.getRoomNumber());

        operationRoom.setMaxPeopleAllowed(4);
        operationRoom.setOccupancy(4);
        assertTrue(operationRoom.isFilled());
        assertEquals(0, operationRoom.getRemainingPlaces());

        Map<String, Integer> newEquipment = new HashMap<>();
        newEquipment.put("scissors", 3);
        operationRoom.setSurgicalEquipment(newEquipment);
        assertEquals(1, operationRoom.getSurgicalEquipment().size());
        assertEquals(3, operationRoom.getSurgicalEquipment().get("scissors"));

        operationRoom.addItem("monitor", 2);
        assertEquals(2, operationRoom.getSurgicalEquipment().get("monitor"));
    }

    @Test
    @SkipSetup
    void testDefaultConstructor() {
        OperationRoom empty = new OperationRoom();

        assertNull(empty.getRoomNumber());
        assertEquals(0, empty.getMaxPeopleAllowed());
        assertEquals(0, empty.getOccupancy());
        assertFalse(empty.isFilled());
        assertNull(empty.getSurgicalEquipment());
    }

    @Test
    void testErrors() {
        OperationRoom or = new OperationRoom("3S", 2, 1, floor);
        assertThrows(IllegalArgumentException.class, () -> or.setOccupancy(5));

        assertThrows(NullPointerException.class, () -> {
            ValidatorService.validate(new OperationRoom());
        });
    }

    @Test
    void testSerialization() {
        String path = PathConstants.ROOMS_TESTS + "test-operation-room.json";
        objectStore.save(operationRoom, path);

        OperationRoom loaded = objectStore.load(OperationRoom.class, path);

        assertEquals("1S", loaded.getRoomNumber());
        assertEquals(3, loaded.getMaxPeopleAllowed());
        assertEquals(2, loaded.getOccupancy());
        assertFalse(loaded.isFilled());
        assertEquals(1, loaded.getRemainingPlaces());

        assertEquals(2, loaded.getSurgicalEquipment().size());
        assertEquals(5, loaded.getSurgicalEquipment().get("scalpel"));
        assertEquals(10, loaded.getSurgicalEquipment().get("clamp"));
    }

    @Test
    void testOperationsManagement() {
        Operation op1 = new Operation(java.sql.Timestamp.valueOf("2025-08-08 11:00:00"));
        Operation op2 = new Operation(java.sql.Timestamp.valueOf("2025-08-08 14:00:00"));

        operationRoom.addOperation(op1);
        operationRoom.addOperation(op2);

        assertEquals(2, operationRoom.getOperations().size());
        assertTrue(operationRoom.getOperations().contains(op1));
        assertTrue(operationRoom.getOperations().contains(op2));

        assertEquals(operationRoom, op1.getOperationRoom());
        assertEquals(operationRoom, op2.getOperationRoom());

        operationRoom.removeOperation(op1);
        assertEquals(1, operationRoom.getOperations().size());
        assertFalse(operationRoom.getOperations().contains(op1));
        assertTrue(operationRoom.getOperations().contains(op2));
        
        assertNull(op1.getOperationRoom());
        assertEquals(operationRoom, op2.getOperationRoom());
    }
}
