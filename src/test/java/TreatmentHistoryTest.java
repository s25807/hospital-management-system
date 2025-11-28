import models.*;
import org.junit.jupiter.api.Test;
import validators.ValidatorService;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TreatmentHistoryTest {

    @Test
    void testValidCreation() {
        Patient p = new Patient(
                "4515515151",
                "username",
                "password123",
                "Jake",
                "Kowalski",
                Date.valueOf("2001-05-05"),
                Person.Nation.PL,
                Patient.BloodType.A,
                true,
                80,
                180,
                true
        );

        Treatment t = new Medicine("Painkillers", 1.0,
                Date.valueOf("2024-11-01"),
                Date.valueOf("2024-11-05"),
                "Pfizer",
                "SN-123");

        AdditionalInformation info = new AdditionalInformation(
                Date.valueOf("2024-11-10"),
                "Patient responded well",
                AdditionalInformation.ImportanceLevel.Low
        );

        TreatmentHistory history = new TreatmentHistory(
                p, t, info, Date.valueOf("2024-11-11")
        );

        assertEquals(p, history.getPatient());
        assertEquals(t, history.getTreatment());
        assertEquals(info, history.getAdditionalInformation());
    }

    @Test
    void testValidationFailsForNullFields() {
        TreatmentHistory history = new TreatmentHistory(
                null, null, null, null
        );

        assertThrows(IllegalArgumentException.class,
                () -> ValidatorService.validate(history));
    }
}
