import models.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OperationTest {
    private Operation operation;

    @BeforeEach
    void setUp() {
        operation = new Operation(Timestamp.valueOf("2025-08-08 11:00:00"));
        operation.setEndTime(Timestamp.valueOf("2025-08-08 11:45:00"));
        operation.setStatus(Operation.Status.Completed);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(Timestamp.valueOf("2025-08-08 11:00:00"), operation.getStartTime());
        assertEquals(Timestamp.valueOf("2025-08-08 11:45:00"), operation.getEndTime());
        assertEquals(Operation.Status.Completed, operation.getStatus());
    }

    @Test
    void testSetters() {
        operation.setStartTime(Timestamp.valueOf("2025-08-09 11:00:00"));
        assertEquals(Timestamp.valueOf("2025-08-09 11:00:00"), operation.getStartTime());

        operation.setEndTime(Timestamp.valueOf("2025-08-09 11:30:00"));
        assertEquals(Timestamp.valueOf("2025-08-09 11:30:00"), operation.getEndTime());

        operation.setStatus(Operation.Status.Ongoing);
        assertEquals(Operation.Status.Ongoing, operation.getStatus());
    }

    @Test
    void testDefaultConstructor() {
        Operation emptyOperation = new Operation();

        assertNull(emptyOperation.getStartTime());
        assertNull(emptyOperation.getEndTime());
        assertNull(emptyOperation.getStatus());
    }
}
