import annotations.SkipSetup;
import constants.PathConstants;
import models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class VanTest {
    private final ObjectStore objectStore = new ObjectStore();
    private MedicalLicense medicalLicense;
    private Van van;
    private Paramedic paramedic;
    private Paramedic driver;


    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        medicalLicense = new MedicalLicense("AAC-DAE-20A", Date.valueOf("2000-10-10"), Date.valueOf("2020-10-10"));
        van = new Van(
                "ABC-123",
                AmbulanceVehicle.Brand.REV,
                1000.5,
                4,
                false,
                120,
                500,
                Van.Capability.Extreme
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
                van
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
                van
        );

        van.addParamedic(paramedic);
        van.addParamedic(driver);
        van.setDriver(driver);
    }

    @Test
    void constructorAndGetSet() {
        assertEquals("ABC-123", van.getRegistrationPlate());
        assertEquals(AmbulanceVehicle.Brand.REV, van.getBrand());
        assertEquals(1000.5, van.getWeightLimit());
        assertEquals(4, van.getPersonLimit());
        assertFalse(van.isOnMission());
        assertEquals(120, van.getMaxSpeed());
        assertEquals(500, van.getRangeOfTravel());
        assertEquals(Van.Capability.Extreme, van.getOffRoadCapability());

        van.setOffRoadCapability(Van.Capability.Low);
        assertEquals(Van.Capability.Low, van.getOffRoadCapability());

        assertEquals(driver,  van.getDriver());
        assertEquals(2, van.getParamedicList().size());
    }

    @Test
    @SkipSetup
    void noArgConstructorDefaults() {
        Van van = new Van();
        assertNull(van.getOffRoadCapability());
        assertNull(van.getRegistrationPlate());
        assertNull(van.getBrand());
    }

    @Test
    void serializationTest() {
        String path = PathConstants.VEHICLES_TESTS + "van-test.json";
        objectStore.save(van, path);

        Van loaded = objectStore.load(Van.class, path);

        assertEquals("ABC-123", loaded.getRegistrationPlate());
        assertEquals(AmbulanceVehicle.Brand.REV, loaded.getBrand());
        assertEquals(1000.5, loaded.getWeightLimit());
        assertEquals(4, loaded.getPersonLimit());
        assertFalse(loaded.isOnMission());
        assertEquals(120, loaded.getMaxSpeed());
        assertEquals(500, loaded.getRangeOfTravel());
        assertEquals(Van.Capability.Extreme, loaded.getOffRoadCapability());

        loaded.setOffRoadCapability(Van.Capability.Low);
        assertEquals(Van.Capability.Low, loaded.getOffRoadCapability());
    }
}
