import models.*;
import org.junit.jupiter.api.Test;
import validators.ValidatorService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        TreatmentHistory ts = new TreatmentHistory(p, new ArrayList<>(), new ArrayList<>(), Date.valueOf(LocalDate.now()));

        Treatment t = new Medicine("Painkillers", 1.0,
                Date.valueOf("2024-11-01"),
                Date.valueOf("2024-11-05"),
                ts,
                "Pfizer",
                "SN-123");

        AdditionalInformation info = new AdditionalInformation(
                Date.valueOf("2024-11-10"),
                "Patient responded well",
                AdditionalInformation.ImportanceLevel.Low
        );

        TreatmentHistory history = new TreatmentHistory(
                p, List.of(t), List.of(info), Date.valueOf("2024-11-11")
        );

        assertEquals(p, history.getPatient());
        assertEquals(t, history.getTreatmentList().get(0));
        assertEquals(info, history.getAdditionalInformationList().get(0));
    }

    @Test
    void testValidationFailsForNullFields() {
        TreatmentHistory history = new TreatmentHistory(
                null, null, null, null
        );

        assertThrows(NullPointerException.class,
                () -> ValidatorService.validate(history));
    }

    //TODO Serialization Test with ObjectStore
}
