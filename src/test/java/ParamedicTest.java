import constants.PathConstants;
import exceptions.InvalidPasswordException;
import models.Paramedic;
import models.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParamedicTest {

    private Paramedic paramedic;
    private Paramedic paramedicWithLicences;

    @BeforeEach
    void setUp() {
        paramedic = new Paramedic(
                "11223344556",
                "paramedicUser",
                "password123",
                "Robert",
                "Williams",
                Date.valueOf("1985-07-20"),
                Person.Nation.ENG,
                "par_1",
                Paramedic.Status.ACTIVE,
                true,
                Paramedic.LicenceType.FIRST_AID,
                "LIC001",
                true,
                "CPR123",
                "ALS001"
        );

        paramedicWithLicences = new Paramedic(
                "33445566778",
                "paramedic2",
                "password123",
                "Tomasz",
                "Dąbrowski",
                Date.valueOf("1980-05-10"),
                Person.Nation.PL,
                "par_2",
                Paramedic.Status.ON_LEAVE,
                false,
                List.of("AA111", "BB222"),
                Paramedic.LicenceType.SURGICAL,
                "LIC777",
                false,
                "CPR445",
                "ALS777"
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("11223344556", paramedic.getPesel());
        assertEquals("paramedicUser", paramedic.getUsername());
        assertEquals("password123", paramedic.getPassword());
        assertEquals("Robert", paramedic.getName());
        assertEquals("Williams", paramedic.getSurname());
        assertEquals(Date.valueOf("1985-07-20"), paramedic.getDob());
        assertEquals(Person.Nation.ENG, paramedic.getNationality());

        assertEquals("par_1", paramedic.getEmployeeId());
        assertEquals(Paramedic.Status.ACTIVE, paramedic.getStatus());
        assertTrue(paramedic.isOnDuty());

        assertEquals(Paramedic.LicenceType.FIRST_AID, paramedic.getLicenceType());
        assertEquals("LIC001", paramedic.getLicenceNumber());
        assertTrue(paramedic.isHasEmergencyDrivingPermit());
        assertEquals("CPR123", paramedic.getCprNumber());
        assertEquals("ALS001", paramedic.getAdvancedLifeSupNumber());


        assertEquals("33445566778", paramedicWithLicences.getPesel());
        assertEquals("paramedic2", paramedicWithLicences.getUsername());
        assertEquals("password123", paramedicWithLicences.getPassword());
        assertEquals("Tomasz", paramedicWithLicences.getName());
        assertEquals("Dąbrowski", paramedicWithLicences.getSurname());
        assertEquals(Date.valueOf("1980-05-10"), paramedicWithLicences.getDob());
        assertEquals(Person.Nation.PL, paramedicWithLicences.getNationality());

        assertEquals("par_2", paramedicWithLicences.getEmployeeId());
        assertEquals(Paramedic.Status.ON_LEAVE, paramedicWithLicences.getStatus());
        assertFalse(paramedicWithLicences.isOnDuty());

        assertEquals(List.of("AA111", "BB222"), paramedicWithLicences.getListOfMedLicenceNumbers());

        assertEquals(Paramedic.LicenceType.SURGICAL, paramedicWithLicences.getLicenceType());
        assertEquals("LIC777", paramedicWithLicences.getLicenceNumber());
        assertFalse(paramedicWithLicences.isHasEmergencyDrivingPermit());
        assertEquals("CPR445", paramedicWithLicences.getCprNumber());
        assertEquals("ALS777", paramedicWithLicences.getAdvancedLifeSupNumber());
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
        paramedic.registerNewMedLicence("MED004");
        assertTrue(paramedic.getListOfMedLicenceNumbers().contains("MED004"));

        assertTrue(paramedic.findMedicalLicenseNumber("MED004").isPresent());
        assertTrue(paramedic.findMedicalLicenseNumber("XYZ").isEmpty());
    }

    @Test
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

        assertNull(empty.getListOfMedLicenceNumbers());
    }

    @Test
    void testErrors() {

        assertThrows(IllegalArgumentException.class, () -> {
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
                                Paramedic.LicenceType.FIRST_AID,
                                "LIC001",
                                true,
                                "CPR123",
                                "ALS001"
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
                                Paramedic.LicenceType.FIRST_AID,
                                "LIC001",
                                true,
                                "CPR123",
                                "ALS001"
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
        ObjectMapper mapper = new ObjectMapper();
        String path = PathConstants.PARAMEDICS_TESTS;

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(path + "test-paramedic.json"), paramedic);

            Paramedic loaded = mapper.readValue(new File(path + "test-paramedic.json"), Paramedic.class);

            assertEquals("11223344556", loaded.getPesel());
            assertEquals("Robert", loaded.getName());
            assertEquals("Williams", loaded.getSurname());
            assertEquals(Date.valueOf("1985-07-20"), loaded.getDob());
            assertEquals(Person.Nation.ENG, loaded.getNationality());

            assertEquals("par_1", loaded.getEmployeeId());
            assertEquals(Paramedic.Status.ACTIVE, loaded.getStatus());
            assertTrue(loaded.isOnDuty());

            assertEquals(Paramedic.LicenceType.FIRST_AID, loaded.getLicenceType());
            assertEquals("LIC001", loaded.getLicenceNumber());
            assertTrue(loaded.isHasEmergencyDrivingPermit());
            assertEquals("CPR123", loaded.getCprNumber());
            assertEquals("ALS001", loaded.getAdvancedLifeSupNumber());

        } catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }
}
