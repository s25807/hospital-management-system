import annotations.SkipSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.PatientRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PatientRoomTest {
    private PatientRoom patientRoom;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String roomPath = PathConstants.ROOMS_TESTS;

    @BeforeEach
    public void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        patientRoom = new PatientRoom("15C", 4, 2, false);
    }

    @Test
    public void gettersAndSettersTest() {
        Assertions.assertNotNull(patientRoom);
        Assertions.assertEquals("15C", patientRoom.getRoomNumber());
        Assertions.assertEquals(4, patientRoom.getMaxPeopleAllowed());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {patientRoom.setOccupancy(5);});
        Assertions.assertFalse(patientRoom.isFilled());
        Assertions.assertFalse(patientRoom.isVip());
        Assertions.assertEquals(2, patientRoom.getRemainingPlaces());
    }

    @Test
    @SkipSetup
    void testDefaultConstructor() {
        PatientRoom empty = new PatientRoom();
        assertNull(empty.getRoomNumber());
        assertEquals(0, empty.getMaxPeopleAllowed());
        assertEquals(0, empty.getOccupancy());
        assertFalse(empty.isFilled());
        assertFalse(empty.isVip());
    }

    @Test
    void testErrors() {
        PatientRoom room = new PatientRoom("300C", 2, 1, false);
        assertThrows(IllegalArgumentException.class, () -> room.setOccupancy(3));

        assertThrows(IllegalArgumentException.class, () -> {
            ValidatorService.validate(new PatientRoom());
        });
    }

    @Test
    public void serializationTest() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(roomPath + "test-pRoom.json"), patientRoom);
            PatientRoom room =  mapper.readValue(new File(roomPath + "test-pRoom.json"), PatientRoom.class);

            Assertions.assertNotNull(room);
            Assertions.assertEquals("15C", room.getRoomNumber());
            Assertions.assertEquals(4, room.getMaxPeopleAllowed());
            Assertions.assertThrows(IllegalArgumentException.class, () -> {room.setOccupancy(5);});
            Assertions.assertFalse(room.isFilled());
            Assertions.assertFalse(room.isVip());
            Assertions.assertEquals(2, room.getRemainingPlaces());
        }
        catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }
}
