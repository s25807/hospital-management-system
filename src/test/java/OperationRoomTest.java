import annotations.SkipSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.Department;
import models.Floor;
import models.Operation;
import models.OperationRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
    void testOperationRoomAssociationsValid() {
        OperationRoom room = new OperationRoom("OR-01", 10, 0, floor);

        Operation op = new Operation(Timestamp.valueOf("2030-01-01 10:00:00"));
        op.setEndTime(Timestamp.valueOf("2030-01-01 11:00:00"));

        room.setOperations(new ArrayList<>(List.of(op)));

        assertEquals(1, room.getOperations().size());
        assertEquals(op, room.getOperations().get(0));
    }

    @Test
    void testOperationRoomSetOperationsFailsOnEmpty() {
        OperationRoom room = new OperationRoom("OR-01", 10, 0, floor);

        assertThrows(IllegalArgumentException.class, () -> room.setOperations(new ArrayList<>()));
    }


}
