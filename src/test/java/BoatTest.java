import annotations.SkipSetup;
import models.Boat;
import models.AmbulanceVehicle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.*;

public class BoatTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private Boat boat;

    private static final String SAMPLE_REG = "ABC-123";
    private static final AmbulanceVehicle.Brand SAMPLE_BRAND = AmbulanceVehicle.Brand.REV;
    private static final double SAMPLE_WEIGHT = 1000.5;
    private static final int SAMPLE_PERSONS = 4;
    private static final boolean SAMPLE_ON_MISSION = true;
    private static final double SAMPLE_MAX_SPEED = 120.0;
    private static final double SAMPLE_RANGE = 500.0;

    @BeforeEach
    public void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        boat = new Boat(
                SAMPLE_REG, SAMPLE_BRAND, SAMPLE_WEIGHT, SAMPLE_PERSONS,
                SAMPLE_ON_MISSION, SAMPLE_MAX_SPEED, SAMPLE_RANGE, 12.34
        );
    }

    @Test
    void constructorAndGetSet() {
        assertEquals(SAMPLE_REG, boat.getRegistrationPlate());
        assertEquals(SAMPLE_BRAND, boat.getBrand());
        assertEquals(SAMPLE_WEIGHT, boat.getWeightLimit());
        assertEquals(SAMPLE_PERSONS, boat.getPersonLimit());
        assertTrue(boat.isOnMission());
        assertEquals(SAMPLE_MAX_SPEED, boat.getMaxSpeed());
        assertEquals(SAMPLE_RANGE, boat.getRangeOfTravel());
        assertEquals(12.34, boat.getLength(), 1e-9);

        boat.setLength(20.0);
        boat.setOnMission(false);
        assertEquals(20.0, boat.getLength());
        assertFalse(boat.isOnMission());
    }

    @Test
    @SkipSetup
    void noArgConstructorDefaults() {
        Boat boat = new Boat();
        assertEquals(0.0, boat.getLength());
        assertNull(boat.getRegistrationPlate());
        assertNull(boat.getBrand());
    }

    @Test
    void jsonRoundTrip() throws JsonProcessingException {
        Boat boat = new Boat(
                SAMPLE_REG, SAMPLE_BRAND, SAMPLE_WEIGHT, SAMPLE_PERSONS,
                SAMPLE_ON_MISSION, SAMPLE_MAX_SPEED, SAMPLE_RANGE, 15.5
        );

        String json = mapper.writeValueAsString(boat);
        assertTrue(json.contains("\"type\":\"boat\""));

        AmbulanceVehicle deserialized = mapper.readValue(json, AmbulanceVehicle.class);
        assertInstanceOf(Boat.class, deserialized);
        assertEquals(15.5, ((Boat) deserialized).getLength());
    }
}
