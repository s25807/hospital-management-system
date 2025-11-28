import models.Medicine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validators.ValidatorService;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MedicineTest {

    private Medicine medicine;

    @BeforeEach
    void setUp() {
        medicine = new Medicine(
                "Painkillers",
                2.0,
                Date.valueOf("2024-11-20"),
                Date.valueOf("2024-11-25"),
                "Pfizer",
                "SN-1234"
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Painkillers", medicine.getName());
        assertEquals(2.0, medicine.getDose());
        assertEquals(Date.valueOf("2024-11-20"), medicine.getStartDate());
        assertEquals(Date.valueOf("2024-11-25"), medicine.getEndDate());
        assertEquals("Pfizer", medicine.getCompany());
        assertEquals("SN-1234", medicine.getSerialNumber());
    }

    @Test
    void testSetters() {
        medicine.setName("Antibiotic");
        medicine.setDose(1.5);
        medicine.setStartDate(Date.valueOf("2024-11-10"));
        medicine.setEndDate(Date.valueOf("2024-11-15"));
        medicine.setCompany("Novartis");
        medicine.setSerialNumber("SN-999");

        assertEquals("Antibiotic", medicine.getName());
        assertEquals(1.5, medicine.getDose());
        assertEquals(Date.valueOf("2024-11-10"), medicine.getStartDate());
        assertEquals(Date.valueOf("2024-11-15"), medicine.getEndDate());
        assertEquals("Novartis", medicine.getCompany());
        assertEquals("SN-999", medicine.getSerialNumber());
    }

    @Test
    void testCalculateTotalTime() {
        assertEquals(5, medicine.calculateTotalTime());
    }

    @Test
    void testValidationFailsForNegativeDose() {
        Medicine invalid = new Medicine(
                "Painkillers",
                -1.0,
                Date.valueOf("2024-11-20"),
                Date.valueOf("2024-11-25"),
                "Novartis",
                "SN-1234"
        );

        assertThrows(IllegalArgumentException.class, () -> ValidatorService.validate(invalid));
    }

    @Test
    void testValidationFailsForEmptyCompany() {
        Medicine invalid = new Medicine(
                "Painkillers",
                2.0,
                Date.valueOf("2024-11-20"),
                Date.valueOf("2024-11-25"),
                "",
                "SN-1234"
        );

        assertThrows(IllegalArgumentException.class, () -> ValidatorService.validate(invalid));
    }
}
