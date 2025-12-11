import annotations.SkipSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.Floor;
import models.Patient;
import models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FloorTest {
    private final ObjectStore objectStore = new ObjectStore();
    private Floor floor;

    @BeforeEach
    void setup(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        floor = new Floor(2, 12);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(2, floor.getNumber());
        assertEquals(12, floor.getAmountOfRooms());
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
        Floor emptyFloor = new Floor();
        assertEquals(0, emptyFloor.getNumber());
        assertEquals(0, emptyFloor.getAmountOfRooms());
    }

    @Test
    void testSerialization() {
        String path = PathConstants.FLOORS_TESTS + "test-floor.json";
        objectStore.save(floor,  path);

        Floor loaded = objectStore.load(Floor.class, path);
        assertEquals(2, loaded.getNumber());
        assertEquals(12, loaded.getAmountOfRooms());
    }
}
