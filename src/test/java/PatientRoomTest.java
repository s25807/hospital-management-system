import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.Patient;
import models.PatientRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class PatientRoomTest {
    private PatientRoom patientRoom;
    private static final String roomPath = PathConstants.ROOMS_TESTS;

    @BeforeEach
    public void setUp() {
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
    public void serializationTest() {
        ObjectMapper mapper = new ObjectMapper();

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
