import models.Therapy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validators.ValidatorService;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TherapyTest {

    private Therapy therapy;

    @BeforeEach
    void setUp() {
        therapy = new Therapy(
                "Radiation block",
                1.0,
                Date.valueOf("2024-09-01"),
                Date.valueOf("2024-09-10"),
                Therapy.TherapyType.Radiation
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Radiation block", therapy.getName());
        assertEquals(1.0, therapy.getDose());
        assertEquals(Date.valueOf("2024-09-01"), therapy.getStartDate());
        assertEquals(Date.valueOf("2024-09-10"), therapy.getEndDate());
        assertEquals(Therapy.TherapyType.Radiation, therapy.getType());
    }

    @Test
    void testSetters() {
        therapy.setName("Chemo block");
        therapy.setDose(0.5);
        therapy.setStartDate(Date.valueOf("2024-10-01"));
        therapy.setEndDate(Date.valueOf("2024-10-05"));
        therapy.setType(Therapy.TherapyType.Chemo);

        assertEquals("Chemo block", therapy.getName());
        assertEquals(0.5, therapy.getDose());
        assertEquals(Date.valueOf("2024-10-01"), therapy.getStartDate());
        assertEquals(Date.valueOf("2024-10-05"), therapy.getEndDate());
        assertEquals(Therapy.TherapyType.Chemo, therapy.getType());
    }

    @Test
    void testCalculateTotalTime() {
        assertEquals(9, therapy.calculateTotalTime());
    }

    @Test
    void testValidationFailsForNullType() {
        Therapy invalid = new Therapy(
                "Some therapy",
                1.0,
                Date.valueOf("2024-09-01"),
                Date.valueOf("2024-09-02"),
                null
        );

        assertThrows(IllegalArgumentException.class, () -> ValidatorService.validate(invalid));
    }
}
