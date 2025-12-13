import annotations.SkipSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.Floor;
import models.PatientRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PatientRoomTest {
    private PatientRoom patientRoom;
    private final ObjectStore objectStore = new ObjectStore();
    private Floor floor;

    @BeforeEach
    public void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        floor = new Floor(1, 15);
        patientRoom = new PatientRoom("15C", 4, 2, false, floor);
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
        Floor floor = new Floor(2, 15);
        PatientRoom room = new PatientRoom("300C", 2, 1, false, floor);
        assertThrows(IllegalArgumentException.class, () -> room.setOccupancy(3));

        assertThrows(NullPointerException.class, () -> {
            ValidatorService.validate(new PatientRoom());
        });
    }

    @Test
    public void serializationTest() {
        String path = PathConstants.ROOMS_TESTS + "test-pRoom.json";
        objectStore.save(patientRoom, path);

        PatientRoom loaded = objectStore.load(PatientRoom.class, path);

        Assertions.assertNotNull(loaded);
        Assertions.assertEquals("15C", loaded.getRoomNumber());
        Assertions.assertEquals(4, loaded.getMaxPeopleAllowed());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {loaded.setOccupancy(5);});
        Assertions.assertFalse(loaded.isFilled());
        Assertions.assertFalse(loaded.isVip());
        Assertions.assertEquals(2, loaded.getRemainingPlaces());
    }
}
