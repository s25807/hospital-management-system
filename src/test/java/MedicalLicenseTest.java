import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import models.MedicalLicense;
import org.junit.jupiter.api.Test;

public class MedicalLicenseTest {
    @Test
    void testValidMedicalLicenseCreation() {
        MedicalLicense license = new MedicalLicense(
                "ML-12345",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2025, 1, 1)
        );

        assertEquals("ML-12345", license.getLicenseNumber());
        assertEquals(LocalDate.of(2020, 1, 1), license.getAcquisitionDate());
        assertEquals(LocalDate.of(2025, 1, 1), license.getExpirationDate());
    }

    @Test
    void testInvalidLicenseNumber() {
        assertThrows(IllegalArgumentException.class, () ->
                new MedicalLicense("", LocalDate.now(), LocalDate.now().plusDays(1))
        );

        assertThrows(IllegalArgumentException.class, () ->
                new MedicalLicense(null, LocalDate.now(), LocalDate.now().plusDays(1))
        );
    }
    @Test
    void testInvalidDates() {
        LocalDate acquisition = LocalDate.of(2025, 1, 1);
        LocalDate expiration = LocalDate.of(2024, 1, 1);

        assertThrows(IllegalArgumentException.class, () ->
                new MedicalLicense("ML-1", acquisition, expiration)
        );
    }

    @Test
    void testIsExpired() {
        MedicalLicense expired = new MedicalLicense(
                "ML-2",
                LocalDate.of(2010, 1, 1),
                LocalDate.now().minusDays(1)
        );
        assertTrue(expired.isExpired());

        MedicalLicense active = new MedicalLicense(
                "ML-3",
                LocalDate.of(2020, 1, 1),
                LocalDate.now().plusDays(10)
        );
        assertFalse(active.isExpired());
    }
}
