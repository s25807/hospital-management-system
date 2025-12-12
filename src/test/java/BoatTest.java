import annotations.SkipSetup;
import constants.PathConstants;
import models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class BoatTest {
    private final ObjectStore objectStore = new ObjectStore();
    private MedicalLicense medicalLicense;
    private Boat boat;
    private Paramedic paramedic;
    private Paramedic driver;

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
        medicalLicense = new MedicalLicense("AAC-DAE-20A", Date.valueOf("2000-10-10"), Date.valueOf("2020-10-10"));

        boat = new Boat(
                "ABC-123",
                AmbulanceVehicle.Brand.REV,
                1000.5,
                4,
                false,
                120,
                500,
                12.34
        );

        paramedic = new Paramedic(
                "33445566778",
                "paramedic2",
                "Qwerty7/",
                "Tomasz",
                "Dąbrowski",
                Date.valueOf("1980-05-10"),
                Person.Nation.PL,
                "par_2",
                Paramedic.Status.ON_LEAVE,
                false,
                medicalLicense,
                Paramedic.LicenceType.SURGICAL,
                "LIC777",
                false,
                "CPR445",
                "ALS777",
                boat
        );

        driver = new Paramedic(
                "51858500910",
                "paramedic3",
                "Qwerty7/",
                "Mariusz",
                "Dąbrowski",
                Date.valueOf("1980-05-10"),
                Person.Nation.PL,
                "par_2",
                Paramedic.Status.ON_LEAVE,
                false,
                medicalLicense,
                Paramedic.LicenceType.SURGICAL,
                "LIC777",
                false,
                "CPR445",
                "ALS777",
                boat
        );

        boat.addParamedic(paramedic);
        boat.addParamedic(driver);
        boat.setDriver(driver);
    }

    @Test
    void constructorAndGetSet() {
        assertEquals(SAMPLE_REG, boat.getRegistrationPlate());
        assertEquals(SAMPLE_BRAND, boat.getBrand());
        assertEquals(SAMPLE_WEIGHT, boat.getWeightLimit());
        assertEquals(SAMPLE_PERSONS, boat.getPersonLimit());
        assertFalse(boat.isOnMission());
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
    void serializationTest() {
        String path = PathConstants.VEHICLES_TESTS + "boat-test.json";
        objectStore.save(boat, path);

        Boat loaded = objectStore.load(Boat.class, path);

        assertEquals("ABC-123", loaded.getRegistrationPlate());
        assertEquals(AmbulanceVehicle.Brand.REV, loaded.getBrand());
        assertEquals(1000.5, loaded.getWeightLimit());
        assertEquals(4, loaded.getPersonLimit());
        assertFalse(loaded.isOnMission());
        assertEquals(120, loaded.getMaxSpeed());
        assertEquals(500, loaded.getRangeOfTravel());
        assertEquals(12.34, loaded.getLength());
    }
}
