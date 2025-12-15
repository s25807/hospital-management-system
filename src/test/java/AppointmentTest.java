import annotations.SkipSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {
    private Appointment appointment;
    private final ObjectStore objectStore = new ObjectStore();

    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
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
    @SkipSetup
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
        String path = PathConstants.APPOINTMENTS_TESTS + "test-appointment.json";
        objectStore.save(appointment, path);

        Appointment loaded = objectStore.load(Appointment.class, path);

        assertEquals(Timestamp.valueOf("2025-08-08 11:00:00"), loaded.getStartTime());
        assertEquals(Timestamp.valueOf("2025-08-08 11:45:00") , loaded.getEndTime());
        assertEquals(Appointment.Status.Completed, loaded.getStatus());
    }

    @Test
    void testPatientAssociation() {
        Patient patient = new Patient("12345678901", "testuser", "password123", "John", "Doe",
                                    Date.valueOf("1990-01-01"), Person.Nation.PL,
                                    Patient.BloodType.A, true, 70.0, 180.0, true);

        assertNull(appointment.getPatient());

        appointment.setPatient(patient);

        assertEquals(patient, appointment.getPatient());
        assertTrue(patient.getAppointments().contains(appointment));

        Patient newPatient = new Patient("09876543210", "newuser", "password123", "Jane", "Smith",
                                       Date.valueOf("1992-01-01"), Person.Nation.PL,
                                       Patient.BloodType.B, true, 65.0, 170.0, true);
        appointment.setPatient(newPatient);

        assertFalse(patient.getAppointments().contains(appointment));
        assertTrue(newPatient.getAppointments().contains(appointment));
        assertEquals(newPatient, appointment.getPatient());

        appointment.setPatient(null);
        assertNull(appointment.getPatient());
        assertFalse(newPatient.getAppointments().contains(appointment));
    }

    @Test
    void testDoctorAssociation() {
        MedicalLicense license = new MedicalLicense("DOC123", Date.valueOf("2020-01-01"), Date.valueOf("2030-01-01"));
        Doctor doctor = new Doctor("12345678901", "docuser", "password123", "Dr. Smith", "Medical",
                                 Date.valueOf("1980-01-01"), Person.Nation.PL,
                                 "DOC001", Doctor.Status.ACTIVE, true, false, license);

        assertNull(appointment.getDoctor());

        appointment.setDoctor(doctor);

        assertEquals(doctor, appointment.getDoctor());
        assertTrue(doctor.getAppointments().contains(appointment));

        Doctor newDoctor = new Doctor("09876543210", "newdoc", "password123", "Dr. Jones", "Health",
                                    Date.valueOf("1982-01-01"), Person.Nation.PL,
                                    "DOC002", Doctor.Status.ACTIVE, true, false, license);
        appointment.setDoctor(newDoctor);

        assertFalse(doctor.getAppointments().contains(appointment));
        assertTrue(newDoctor.getAppointments().contains(appointment));
        assertEquals(newDoctor, appointment.getDoctor());

        appointment.setDoctor(null);
        assertNull(appointment.getDoctor());
        assertFalse(newDoctor.getAppointments().contains(appointment));
    }

    @Test
    void testNurseAssociation() {
        MedicalLicense license = new MedicalLicense("NUR123", Date.valueOf("2020-01-01"), Date.valueOf("2030-01-01"));
        Nurse nurse = new Nurse("12345678901", "nurseuser", "password123", "Nurse", "Johnson",
                              Date.valueOf("1985-01-01"), Person.Nation.PL,
                              "NUR001", Nurse.Status.ACTIVE, true, license);

        assertNull(appointment.getNurse());

        appointment.setNurse(nurse);

        assertEquals(nurse, appointment.getNurse());
        assertTrue(nurse.getAppointments().contains(appointment));

        Nurse newNurse = new Nurse("09876543210", "newnurse", "password123", "Nurse", "Williams",
                                 Date.valueOf("1987-01-01"), Person.Nation.PL,
                                 "NUR002", Doctor.Status.ACTIVE, true, license);
        appointment.setNurse(newNurse);

        assertFalse(nurse.getAppointments().contains(appointment));
        assertTrue(newNurse.getAppointments().contains(appointment));
        assertEquals(newNurse, appointment.getNurse());

        appointment.setNurse(null);
        assertNull(appointment.getNurse());
        assertFalse(newNurse.getAppointments().contains(appointment));
    }

    @Test
    void testCompleteAppointmentAssociations() {
        Patient patient = new Patient("12345678901", "testuser", "password123", "John", "Doe",
                                    Date.valueOf("1990-01-01"), Person.Nation.PL,
                                    Patient.BloodType.A, true, 70.0, 180.0, true);

        MedicalLicense license = new MedicalLicense("DOC123", Date.valueOf("2020-01-01"), Date.valueOf("2030-01-01"));
        Doctor doctor = new Doctor("12345678902", "docuser", "password123", "Dr. Smith", "Medical",
                                 Date.valueOf("1980-01-01"), Person.Nation.PL,
                                 "DOC001", Doctor.Status.ACTIVE, true, false, license);

        Nurse nurse = new Nurse("12345678903", "nurseuser", "password123", "Nurse", "Johnson",
                              Date.valueOf("1985-01-01"), Person.Nation.PL,
                              "NUR001", Nurse.Status.ACTIVE, true, license);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setNurse(nurse);

        assertEquals(patient, appointment.getPatient());
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(nurse, appointment.getNurse());

        assertTrue(patient.getAppointments().contains(appointment));
        assertTrue(doctor.getAppointments().contains(appointment));
        assertTrue(nurse.getAppointments().contains(appointment));

        assertEquals(1, patient.getAppointments().size());
        assertEquals(1, doctor.getAppointments().size());
        assertEquals(1, nurse.getAppointments().size());
    }
}
