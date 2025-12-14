import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import annotations.SkipSetup;
import models.Doctor;
import models.Employee;
import models.MedicalLicense;
import models.Nurse;
import models.Operation;
import models.OperationRoom;
import models.Patient;
import models.Person;

public class OperationTest {
    private Operation operation;

    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
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
    @SkipSetup
    void testDefaultConstructor() {
        Operation emptyOperation = new Operation();

        assertNull(emptyOperation.getStartTime());
        assertNull(emptyOperation.getEndTime());
        assertNull(emptyOperation.getStatus());
    }

    @Test
    void testOperationAssociations() {
        OperationRoom opRoom = new OperationRoom("OR001", 10, 8, null);
        MedicalLicense license = new MedicalLicense("LIC001", java.sql.Date.valueOf("2020-01-01"), java.sql.Date.valueOf("2025-01-01"));
        Doctor surgeon = new Doctor("11111111111", "doctor1", "password123", "John", "Doe",
                java.sql.Date.valueOf("1985-05-15"), Person.Nation.PL, "DOC001",
                Employee.Status.ACTIVE, true, true, license);
        Nurse nurse1 = new Nurse("22222222222", "nurse1", "password123", "Anna", "Smith",
                java.sql.Date.valueOf("1990-03-10"), Person.Nation.DE, "NUR001",
                Employee.Status.ACTIVE, true, license);
        Nurse nurse2 = new Nurse("33333333333", "nurse2", "password123", "Maria", "Johnson",
                java.sql.Date.valueOf("1988-01-01"), Person.Nation.PL, "NUR002",
                Employee.Status.ACTIVE, true, license);
        Patient patient = new Patient("12345678901", "patient1", "password123", "Bob", "Wilson",
                java.sql.Date.valueOf("1975-03-10"), Person.Nation.DE, Patient.BloodType.A, true, 75.5, 175.0, true);

        operation.setOperationRoom(opRoom);
        assertEquals(opRoom, operation.getOperationRoom());
        assertTrue(opRoom.getOperations().contains(operation));

        operation.setSurgeon(surgeon);
        assertEquals(surgeon, operation.getSurgeon());
        assertTrue(surgeon.getOperations().contains(operation));

        operation.addAssistingNurse(nurse1);
        operation.addAssistingNurse(nurse2);
        assertEquals(2, operation.getAssistingNurses().size());
        assertTrue(operation.getAssistingNurses().contains(nurse1));
        assertTrue(operation.getAssistingNurses().contains(nurse2));
        assertTrue(nurse1.getOperations().contains(operation));
        assertTrue(nurse2.getOperations().contains(operation));

        operation.setPatient(patient);
        assertEquals(patient, operation.getPatient());

        operation.removeAssistingNurse(nurse1);
        assertEquals(1, operation.getAssistingNurses().size());
        assertFalse(operation.getAssistingNurses().contains(nurse1));
        assertFalse(nurse1.getOperations().contains(operation));
        assertTrue(operation.getAssistingNurses().contains(nurse2));
    }

    @Test
    void testBidirectionalRelationships() {
        OperationRoom opRoom1 = new OperationRoom("OR001", 10, 8, null);
        OperationRoom opRoom2 = new OperationRoom("OR002", 12, 10, null);
        MedicalLicense license = new MedicalLicense("LIC001", java.sql.Date.valueOf("2020-01-01"), java.sql.Date.valueOf("2025-01-01"));
        Doctor surgeon1 = new Doctor("11111111111", "doctor1", "password123", "John", "Doe",
                java.sql.Date.valueOf("1985-05-15"), Person.Nation.PL, "DOC001",
                Employee.Status.ACTIVE, true, true, license);
        Doctor surgeon2 = new Doctor("44444444444", "doctor2", "password123", "Jane", "Brown",
                java.sql.Date.valueOf("1982-07-20"), Person.Nation.ENG, "DOC002",
                Employee.Status.ACTIVE, false, false, license);

        operation.setOperationRoom(opRoom1);
        assertTrue(opRoom1.getOperations().contains(operation));

        operation.setOperationRoom(opRoom2);
        assertFalse(opRoom1.getOperations().contains(operation));
        assertTrue(opRoom2.getOperations().contains(operation));

        operation.setSurgeon(surgeon1);
        assertTrue(surgeon1.getOperations().contains(operation));

        operation.setSurgeon(surgeon2);
        assertFalse(surgeon1.getOperations().contains(operation));
        assertTrue(surgeon2.getOperations().contains(operation));

        operation.setOperationRoom(null);
        assertFalse(opRoom2.getOperations().contains(operation));

        operation.setSurgeon(null);
        assertFalse(surgeon2.getOperations().contains(operation));
    }

    //TODO Serialization test with ObjectStore
}
