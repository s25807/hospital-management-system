import annotations.SkipSetup;
import constants.PathConstants;
import exceptions.InvalidPasswordException;
import models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;
import validators.ValidatorService;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ParamedicTest {
    private final ObjectStore objectStore = new ObjectStore();
    private Paramedic paramedic;
    private MedicalLicense medicalLicense;
    private Van van;

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

        van.addParamedic(paramedic);
        van.setDriver(paramedic);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("33445566778", paramedic.getPesel());
        assertEquals("paramedic2", paramedic.getUsername());
        assertEquals("Qwerty7/", paramedic.getPassword());
        assertEquals("Tomasz", paramedic.getName());
        assertEquals("Dąbrowski", paramedic.getSurname());
        assertEquals(Date.valueOf("1980-05-10"), paramedic.getDob());
        assertEquals(Person.Nation.PL, paramedic.getNationality());

        assertEquals("par_2", paramedic.getEmployeeId());
        assertEquals(Paramedic.Status.ON_LEAVE, paramedic.getStatus());
        assertFalse(paramedic.isOnDuty());

        assertEquals(Map.of(medicalLicense.getLicenseNumber(), medicalLicense), paramedic.getMapOfMedLicenceNumbers());

        assertEquals(Paramedic.LicenceType.SURGICAL, paramedic.getLicenceType());
        assertEquals("LIC777", paramedic.getLicenceNumber());
        assertFalse(paramedic.isHasEmergencyDrivingPermit());
        assertEquals("CPR445", paramedic.getCprNumber());
        assertEquals("ALS777", paramedic.getAdvancedLifeSupNumber());
    }

    @Test
    void testSetters() {
        paramedic.setLicenceType(Paramedic.LicenceType.SURGICAL);
        assertEquals(Paramedic.LicenceType.SURGICAL, paramedic.getLicenceType());

        paramedic.setLicenceNumber("ZZ999");
        assertEquals("ZZ999", paramedic.getLicenceNumber());

        paramedic.setHasEmergencyDrivingPermit(false);
        assertFalse(paramedic.isHasEmergencyDrivingPermit());

        paramedic.setCprNumber("CPR777");
        assertEquals("CPR777", paramedic.getCprNumber());

        paramedic.setAdvancedLifeSupNumber("ALS333");
        assertEquals("ALS333", paramedic.getAdvancedLifeSupNumber());

        paramedic.toggleDuty(false);
        assertFalse(paramedic.isOnDuty());
    }

    @Test
    void testMedicalLicenceOperations() {
        paramedic.registerNewMedLicence(medicalLicense);
        assertTrue(paramedic.getMapOfMedLicenceNumbers().containsValue(medicalLicense));

        assertTrue(paramedic.findMedicalLicenseNumber("AAC-DAE-20A").isPresent());
        assertTrue(paramedic.findMedicalLicenseNumber("XYZ").isEmpty());
    }

    @Test
    @SkipSetup
    void testDefaultConstructor() {
        Paramedic empty = new Paramedic();

        assertNull(empty.getPesel());
        assertNull(empty.getUsername());
        assertNull(empty.getPassword());
        assertNull(empty.getName());
        assertNull(empty.getSurname());
        assertNull(empty.getDob());
        assertNull(empty.getNationality());

        assertNull(empty.getEmployeeId());
        assertNull(empty.getStatus());
        assertFalse(empty.isOnDuty());

        assertNull(empty.getLicenceType());
        assertNull(empty.getLicenceNumber());
        assertFalse(empty.isHasEmergencyDrivingPermit());
        assertNull(empty.getCprNumber());
        assertNull(empty.getAdvancedLifeSupNumber());

        assertThrows(NullPointerException.class, () -> { empty.getMapOfMedLicenceNumbers(); });
    }

    @Test
    void testErrors() {

        assertThrows(NullPointerException.class, () -> {
            ValidatorService.validate(new Paramedic());
        });

        assertThrows(Exception.class, () -> {
            try {
                ValidatorService.validate(
                        new Paramedic(
                                "11223344556",
                                "username",
                                "pass",
                                "Robert",
                                "Williams",
                                Date.valueOf("1985-07-20"),
                                Person.Nation.ENG,
                                "par_1",
                                Paramedic.Status.ACTIVE,
                                true,
                                medicalLicense,
                                Paramedic.LicenceType.FIRST_AID,
                                "LIC001",
                                true,
                                "CPR123",
                                "ALS001",
                                van
                        )
                );
            } catch (Exception e) {
                assertTrue(
                        e instanceof InvalidPasswordException ||
                                e instanceof IllegalArgumentException
                );
                throw e;
            }
        });

        assertThrows(Exception.class, () -> {
            try {
                ValidatorService.validate(
                        new Paramedic(
                                "11223344556",
                                "username",
                                "password", // weak but >= 8 chars
                                "Robert",
                                "Williams",
                                Date.valueOf("1985-07-20"),
                                Person.Nation.ENG,
                                "par_1",
                                Paramedic.Status.ACTIVE,
                                true,
                                medicalLicense,
                                Paramedic.LicenceType.FIRST_AID,
                                "LIC001",
                                true,
                                "CPR123",
                                "ALS001",
                                van
                        )
                );
            } catch (Exception e) {
                assertTrue(
                        e instanceof InvalidPasswordException ||
                                e instanceof IllegalArgumentException
                );
                throw e;
            }
        });
    }

    @Test
    void testSerialization() {
        String path = PathConstants.PARAMEDICS_TESTS + "test-paramedic.json";
        objectStore.save(paramedic, path);

        Paramedic loaded = objectStore.load(Paramedic.class, path);
        assertEquals("33445566778", loaded.getPesel());
        assertEquals("Tomasz", loaded.getName());
        assertEquals("Dąbrowski", loaded.getSurname());
        assertEquals(Date.valueOf("1980-05-10"), loaded.getDob());
        assertEquals(Person.Nation.PL, loaded.getNationality());

        assertEquals("par_2", loaded.getEmployeeId());
        assertEquals(Paramedic.Status.ON_LEAVE, loaded.getStatus());
        assertFalse(loaded.isOnDuty());

        assertEquals(Paramedic.LicenceType.SURGICAL, loaded.getLicenceType());
        assertEquals("LIC777", loaded.getLicenceNumber());
        assertFalse(loaded.isHasEmergencyDrivingPermit());
        assertEquals("CPR445", loaded.getCprNumber());
        assertEquals("ALS777", loaded.getAdvancedLifeSupNumber());
    }
}
