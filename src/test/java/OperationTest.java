import annotations.SkipSetup;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import validators.ValidatorService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void testOperationAssociationsValid() throws Exception {
        Patient patient = new Patient(
                "45155151519",
                "username",
                "Qwerty7/",
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

        Doctor doctor = new Doctor(
                "62627216711",
                "supervisor",
                "password123",
                "Marcus",
                "Smith",
                Date.valueOf("1975-07-20"),
                Person.Nation.ENG,
                "doc_2",
                Doctor.Status.ACTIVE,
                false,
                true,
                new MedicalLicense()
        );

        Nurse nurse = new Nurse(
                "22222222222",
                "nurse2",
                "Qwerty7/",
                "Maria",
                "Woźniak",
                Date.valueOf("1988-01-01"),
                Person.Nation.PL,
                "nur_2",
                Nurse.Status.ON_LEAVE,
                false,
                new MedicalLicense()
        );

        Department dep = new Department("14", "Department of Neurology");
        Floor floor = new Floor(1, 15, dep);

        OperationRoom room = new OperationRoom("OR-01", 10, 0, floor);


        Operation operation = new Operation(Timestamp.valueOf("2030-01-01 10:00:00"));
        operation.setEndTime(Timestamp.valueOf("2030-01-01 11:00:00"));

        operation.setPatients(List.of(patient));
        operation.setDoctors(List.of(doctor));
        operation.setOperationRoom(room);
        operation.setNurses(new ArrayList<>(List.of(nurse)));

        assertEquals(patient, operation.getPatients().get(0));
        assertEquals(doctor, operation.getDoctors().get(0));
        assertEquals(room, operation.getOperationRoom());
        assertEquals(1, operation.getNurses().size());
    }

    @Test
    void testOperationSetNursesFailsOnEmpty() {
        Operation operation = new Operation(Timestamp.valueOf("2030-01-01 10:00:00"));
        assertThrows(NullPointerException.class, () -> { operation.setNurses(new ArrayList<>()); ValidatorService.validate(operation); });
    }

    @Test
    void testOperationSetDoctorFailsOnNull() {
        Operation operation = new Operation(Timestamp.valueOf("2030-01-01 10:00:00"));
        assertThrows(NullPointerException.class, () -> operation.setDoctors(List.of(null)));
    }



    //TODO Serialization test with ObjectStore
}