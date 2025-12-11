import models.AdditionalInformation;
import org.junit.jupiter.api.Test;
import validators.ValidatorService;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AdditionalInformationTest {

    @Test
    void testValidCreation() {
        AdditionalInformation info = new AdditionalInformation(
                Date.valueOf("2024-11-20"),
                "Patient has mild fever",
                AdditionalInformation.ImportanceLevel.Medium
        );

        assertEquals("Patient has mild fever", info.getInformation());
        assertEquals(AdditionalInformation.ImportanceLevel.Medium, info.getImportance());
    }

    @Test
    void testValidationFailsForNullFields() {
        AdditionalInformation info = new AdditionalInformation(
                null, null, null
        );

        assertThrows(NullPointerException.class,
                () -> ValidatorService.validate(info));
    }

    //TODO Serialization test
}
