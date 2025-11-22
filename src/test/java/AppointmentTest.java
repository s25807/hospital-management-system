import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.Appointment;
import models.Patient;
import models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment(Timestamp.valueOf("2025-08-08 11:00:00"));
        appointment.setEndTime(Timestamp.valueOf("2025-08-08 11:45:00"));
        appointment.setStatus(Appointment.Status.Completed);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(Timestamp.valueOf("2025-08-08 11:00:00"), appointment.getStartTime());
        assertEquals(Timestamp.valueOf("2025-08-08 11:45:00"), appointment.getEndTime());
        assertEquals(Appointment.Status.Completed, appointment.getStatus());
    }

    @Test
    void testSetters() {
        appointment.setStartTime(Timestamp.valueOf("2025-08-09 11:00:00"));
        assertEquals(Timestamp.valueOf("2025-08-09 11:00:00"), appointment.getStartTime());

        appointment.setEndTime(Timestamp.valueOf("2025-08-09 11:30:00"));
        assertEquals(Timestamp.valueOf("2025-08-09 11:30:00"), appointment.getEndTime());

        appointment.setStatus(Appointment.Status.Scheduled);
        assertEquals(Appointment.Status.Scheduled, appointment.getStatus());
    }

    @Test
    void testDefaultConstructor() {
        Appointment emptyAppointment = new Appointment();

        assertNull(emptyAppointment.getStartTime());
        assertNull(emptyAppointment.getEndTime());
        assertNull(emptyAppointment.getStatus());
    }

    @Test
    void testCalculateDuration() {
        assertEquals(45, appointment.calculateDuration());
    }

    @Test
    void testSerialization() {
        ObjectMapper mapper = new ObjectMapper();
        String path = PathConstants.APPOINTMENTS_TESTS;

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path + "test-appointment.json"), appointment);

            Appointment loaded = mapper.readValue(new File(path + "test-appointment.json"), Appointment.class);

            assertEquals(Timestamp.valueOf("2025-08-08 11:00:00"), loaded.getStartTime());
            assertEquals(Timestamp.valueOf("2025-08-08 11:45:00") , loaded.getEndTime());
            assertEquals(Appointment.Status.Completed, loaded.getStatus());
        }
        catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }
}
