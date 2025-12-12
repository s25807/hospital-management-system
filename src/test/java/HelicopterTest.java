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

public class HelicopterTest {
    private final ObjectStore objectStore = new ObjectStore();
    private Helicopter heli;
    private MedicalLicense medicalLicense;
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
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        medicalLicense = new MedicalLicense("AAC-DAE-20A", Date.valueOf("2000-10-10"), Date.valueOf("2020-10-10"));
        heli = new Helicopter(
                SAMPLE_REG, SAMPLE_BRAND, SAMPLE_WEIGHT, SAMPLE_PERSONS,
                SAMPLE_ON_MISSION, SAMPLE_MAX_SPEED, SAMPLE_RANGE, 3000.0
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
                heli
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
                heli
        );

        heli.addParamedic(paramedic);
        heli.addParamedic(driver);
        heli.setDriver(driver);
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
    void serializationTest() {
        String path = PathConstants.VEHICLES_TESTS + "heli-test.json";
        objectStore.save(heli, path);

        Helicopter loaded = objectStore.load(Helicopter.class, path);

        assertEquals("ABC-123", loaded.getRegistrationPlate());
        assertEquals(AmbulanceVehicle.Brand.REV, loaded.getBrand());
        assertEquals(1000.5, loaded.getWeightLimit());
        assertEquals(4, loaded.getPersonLimit());
        assertTrue(loaded.isOnMission());
        assertEquals(120, loaded.getMaxSpeed());
        assertEquals(500, loaded.getRangeOfTravel());
        assertEquals(3000.0, loaded.getMaxAltitude());
    }
}
