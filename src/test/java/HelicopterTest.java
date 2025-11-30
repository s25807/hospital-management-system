import annotations.SkipSetup;
import models.Helicopter;
import models.AmbulanceVehicle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.*;

public class HelicopterTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private Helicopter heli;

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
        heli = new Helicopter(
                SAMPLE_REG, SAMPLE_BRAND, SAMPLE_WEIGHT, SAMPLE_PERSONS,
                SAMPLE_ON_MISSION, SAMPLE_MAX_SPEED, SAMPLE_RANGE, 3000.0
        );
    }

    @Test
    void constructorAndGetSet() {
        assertEquals(3000.0, heli.getMaxAltitude());
        heli.setMaxAltitude(3500.5);
        assertEquals(3500.5, heli.getMaxAltitude());
    }

    @Test
    @SkipSetup
    void noArgConstructorDefaults() {
        Helicopter heli = new Helicopter();
        assertEquals(0.0, heli.getMaxAltitude());
        assertNull(heli.getRegistrationPlate());
        assertNull(heli.getBrand());
    }

    @Test
    void jsonRoundTrip() throws JsonProcessingException {
        Helicopter heli = new Helicopter(
                SAMPLE_REG, SAMPLE_BRAND, SAMPLE_WEIGHT, SAMPLE_PERSONS,
                SAMPLE_ON_MISSION, SAMPLE_MAX_SPEED, SAMPLE_RANGE, 4200.0
        );

        String json = mapper.writeValueAsString(heli);
        assertTrue(json.contains("\"type\":\"helicopter\""));

        AmbulanceVehicle deser = mapper.readValue(json, AmbulanceVehicle.class);
        assertInstanceOf(Helicopter.class, deser);
        assertEquals(4200.0, ((Helicopter) deser).getMaxAltitude());
    }
}
