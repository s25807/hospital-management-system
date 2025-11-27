import models.Treatment;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TreatmentTest {

    static class TestTreatment extends Treatment {
        public TestTreatment(String name, double dose, Date startDate, Date endDate) {
            super(name, dose, startDate, endDate);
        }
    }

    @Test
    void testCalculateTotalTimeReturnsCorrectDays() {
        Date start = Date.valueOf("2024-11-20");
        Date end = Date.valueOf("2024-11-25");

        Treatment treatment = new TestTreatment("Test", 1.0, start, end);

        assertEquals(5, treatment.calculateTotalTime());
    }

    @Test
    void testCalculateTotalTimeThrowsWhenDatesNull() {
        Treatment treatment = new TestTreatment("Test", 1.0, null, null);

        assertThrows(IllegalStateException.class, treatment::calculateTotalTime);
    }

    @Test
    void testCalculateTotalTimeThrowsWhenEndBeforeStart() {
        Date start = Date.valueOf("2024-11-25");
        Date end = Date.valueOf("2024-11-20");

        Treatment treatment = new TestTreatment("Test", 1.0, start, end);

        assertThrows(IllegalArgumentException.class, treatment::calculateTotalTime);
    }
}
