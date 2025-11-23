import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.Floor;
import models.Patient;
import models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FloorTest {
    private Floor floor;

    @BeforeEach
    void setup() {
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
    void testDefaultConstructor() {
        Floor emptyFloor = new Floor();
        assertEquals(0, emptyFloor.getNumber());
        assertEquals(0, emptyFloor.getAmountOfRooms());
    }

    @Test
    void testSerialization() {
        ObjectMapper mapper = new ObjectMapper();
        String path = PathConstants.FLOORS_TESTS;

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path + "test-floor.json"), floor);
            Floor loaded = mapper.readValue(new File(path + "test-floor.json"), Floor.class);
            assertEquals(2, loaded.getNumber());
            assertEquals(12, loaded.getAmountOfRooms());
        }
        catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }
}
