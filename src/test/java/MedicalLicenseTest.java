import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;

import models.MedicalLicense;
import org.junit.jupiter.api.Test;

public class MedicalLicenseTest {
    @Test
    void testValidMedicalLicenseCreation() {
        MedicalLicense license = new MedicalLicense(
                "ML-12345",
                Date.valueOf("2020-01-01"),
                Date.valueOf("2020-01-01")
        );

        assertEquals("ML-12345", license.getLicenseNumber());
        assertEquals(Date.valueOf("2020-01-01"), license.getAcquisitionDate());
        assertEquals(Date.valueOf("2020-01-01"), license.getExpirationDate());
    }

    @Test
    void testInvalidLicenseNumber() {
        assertThrows(IllegalArgumentException.class, () ->
                new MedicalLicense("", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(1)))
        );

        assertThrows(IllegalArgumentException.class, () ->
                new MedicalLicense(null, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(1)))
        );
    }

    @Test
    void testIsExpired() {
        MedicalLicense expired = new MedicalLicense(
                "ML-2",
                Date.valueOf(LocalDate.of(2020, 1, 1)),
                Date.valueOf(LocalDate.now().minusDays(1))
        );
        assertTrue(expired.isExpired());

        MedicalLicense active = new MedicalLicense(
                "ML-3",
                Date.valueOf(LocalDate.of(2020, 1, 1)),
                Date.valueOf(LocalDate.now().plusDays(1))
        );
        assertFalse(active.isExpired());
    }

    //TODO Serialization test with ObjectStore
}
