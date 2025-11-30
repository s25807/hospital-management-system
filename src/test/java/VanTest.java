import annotations.SkipSetup;
import models.Van;
import models.AmbulanceVehicle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.*;

public class VanTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private Van van;

    private static final String SAMPLE_REG = "ABC-123";
    private static final AmbulanceVehicle.Brand SAMPLE_BRAND = AmbulanceVehicle.Brand.REV;
    private static final double SAMPLE_WEIGHT = 1000.5;
    private static final int SAMPLE_PERSONS = 4;
    private static final boolean SAMPLE_ON_MISSION = true;
    private static final double SAMPLE_MAX_SPEED = 120.0;
    private static final double SAMPLE_RANGE = 500.0;

    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        van = new Van(
                SAMPLE_REG, SAMPLE_BRAND, SAMPLE_WEIGHT, SAMPLE_PERSONS,
                SAMPLE_ON_MISSION, SAMPLE_MAX_SPEED, SAMPLE_RANGE, true
        );
    }

    @Test
    void constructorAndGetSet() {
        assertEquals(SAMPLE_REG, van.getRegistrationPlate());
        assertEquals(SAMPLE_BRAND, van.getBrand());
        assertEquals(SAMPLE_WEIGHT, van.getWeightLimit());
        assertEquals(SAMPLE_PERSONS, van.getPersonLimit());
        assertTrue(van.isOnMission());
        assertEquals(SAMPLE_MAX_SPEED, van.getMaxSpeed());
        assertEquals(SAMPLE_RANGE, van.getRangeOfTravel());
        assertTrue(van.isOffRoadCapability());

        van.setOffRoadCapability(false);
        assertFalse(van.isOffRoadCapability());
    }

    @Test
    @SkipSetup
    void noArgConstructorDefaults() {
        Van van = new Van();
        assertFalse(van.isOffRoadCapability());
        assertNull(van.getRegistrationPlate());
        assertNull(van.getBrand());
    }

    @Test
    void jsonRoundTrip_VanUppercaseName() throws JsonProcessingException {

        String json = mapper.writeValueAsString(van);

        assertTrue(json.contains("\"type\":\"Van\""));

        AmbulanceVehicle deserialized = mapper.readValue(json, AmbulanceVehicle.class);
        assertInstanceOf(Van.class, deserialized);

        String lowerCaseTypeJson = json.replace("\"type\":\"Van\"", "\"type\":\"van\"");
        assertThrows(InvalidTypeIdException.class,
                () -> mapper.readValue(lowerCaseTypeJson, AmbulanceVehicle.class));
    }
}
