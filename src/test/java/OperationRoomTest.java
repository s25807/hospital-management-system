import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.OperationRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OperationRoomTest {

    private OperationRoom operationRoom;
    private Map<String, Integer> equipment;

    @BeforeEach
    void setUp() {
        equipment = new HashMap<>();
        equipment.put("scalpel", 5);
        equipment.put("clamp", 10);

        operationRoom = new OperationRoom("1S", 3, 2, equipment);
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
        OperationRoom or = new OperationRoom("2S", 2, 1);
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
        OperationRoom or = new OperationRoom("3S", 2, 1);
        assertThrows(IllegalArgumentException.class, () -> or.setOccupancy(5));

        assertThrows(IllegalArgumentException.class, () -> {
            ValidatorService.validate(new OperationRoom());
        });
    }

    @Test
    void testSerialization() {
        ObjectMapper mapper = new ObjectMapper();
        String path = PathConstants.ROOMS_TESTS;

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(path + "test-operation-room.json"), operationRoom);

            OperationRoom loaded = mapper.readValue(
                    new File(path + "test-operation-room.json"),
                    OperationRoom.class
            );

            assertEquals("1S", loaded.getRoomNumber());
            assertEquals(3, loaded.getMaxPeopleAllowed());
            assertEquals(2, loaded.getOccupancy());
            assertFalse(loaded.isFilled());
            assertEquals(1, loaded.getRemainingPlaces());

            assertEquals(2, loaded.getSurgicalEquipment().size());
            assertEquals(5, loaded.getSurgicalEquipment().get("scalpel"));
            assertEquals(10, loaded.getSurgicalEquipment().get("clamp"));
        } catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }
}
