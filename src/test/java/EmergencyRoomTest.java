import annotations.SkipSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import util.ObjectStore;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EmergencyRoomTest {
    private final ObjectStore objectStore = new ObjectStore();
    private EmergencyRoom emergencyRoom;
    private MedicalLicense medicalLicense;
    private Van van;
    private Paramedic paramedic;
    private Floor floor;

    @BeforeEach
    void setUp(TestInfo info) {
        if (info.getTestMethod().map(m -> m.isAnnotationPresent(SkipSetup.class)).orElse(false)) return;
        EmergencyRoom.setResponseTimes(new ArrayList<>());
        floor = new Floor(1, 15);
        medicalLicense = new MedicalLicense("AAC-DAE-20A", Date.valueOf("2000-10-10"), Date.valueOf("2020-10-10"));
        van = new Van(
                "ABC-123",
                AmbulanceVehicle.Brand.REV,
                1000.5,
                4,
                false,
                120,
                500,
                Van.Capability.Extreme
        );

        paramedic = new Paramedic(
                "33445566778",
                "paramedic2",
                "Qwerty7/",
                "Tomasz",
                "Dąbrowski",
                Date.valueOf("1980-05-10"),
                Person.Nation.PL,
                "par_2",
                Paramedic.Status.ON_LEAVE,
                false,
                medicalLicense,
                Paramedic.LicenceType.SURGICAL,
                "LIC777",
                false,
                "CPR445",
                "ALS777",
                van
        );

        van.addParamedic(paramedic);
        van.setDriver(paramedic);
        emergencyRoom = new EmergencyRoom("13E", 5, 2, floor);
        emergencyRoom.addAmbulanceVehicle(van);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("13E", emergencyRoom.getRoomNumber());
        assertEquals(5, emergencyRoom.getMaxPeopleAllowed());
        assertEquals(2, emergencyRoom.getOccupancy());
        assertFalse(emergencyRoom.isFilled());
        assertEquals(3, emergencyRoom.getRemainingPlaces());
    }

    @Test
    @SkipSetup
    void testDefaultConstructor() {
        EmergencyRoom empty = new EmergencyRoom();

        assertNull(empty.getRoomNumber());
        assertEquals(0, empty.getMaxPeopleAllowed());
        assertEquals(0, empty.getOccupancy());
        assertFalse(empty.isFilled());
    }

    @Test
    void testAddResponseTimeAndAverage() {
        Timestamp t1Start = Timestamp.valueOf("2025-01-01 10:00:00");
        Timestamp t1End   = Timestamp.valueOf("2025-01-01 10:10:00");

        Timestamp t2Start = Timestamp.valueOf("2025-01-01 11:00:00");
        Timestamp t2End   = Timestamp.valueOf("2025-01-01 11:20:00");

        emergencyRoom.addResponseTime(t1Start, t1End);
        emergencyRoom.addResponseTime(t2Start, t2End);

        double avg = emergencyRoom.calculateAveResponseTime();
        assertEquals(15.0, avg, 0.0001);
    }

    @Test
    @SkipSetup
    void testResponseTimeClass() {
        Timestamp start = Timestamp.valueOf("2025-01-01 08:00:00");
        Timestamp end   = Timestamp.valueOf("2025-01-01 08:30:00");

        EmergencyRoom.ResponseTime rt = new EmergencyRoom.ResponseTime(start, end);
        assertEquals(start, rt.getStartTime());
        assertEquals(end, rt.getEndTime());
        assertEquals(30.0, rt.getDurationMinutes(), 0.0001);

        Timestamp newEnd = Timestamp.valueOf("2025-01-01 08:45:00");
        rt.setEndTime(newEnd);
        assertEquals(newEnd, rt.getEndTime());
        assertEquals(45.0, rt.getDurationMinutes(), 0.0001);
    }

    @Test
    void testErrors() {
        assertThrows(NullPointerException.class, () -> {
            ValidatorService.validate(new EmergencyRoom());
        });

        EmergencyRoom.ResponseTime rt = new EmergencyRoom.ResponseTime();
        assertThrows(NullPointerException.class, () -> {
            ValidatorService.validate(rt);
        });
    }

    @Test
    void testSerialization() {
        String path = PathConstants.ROOMS_TESTS + "test-emergency-room.json";
        objectStore.save(emergencyRoom,  path);

        EmergencyRoom loaded = objectStore.load(EmergencyRoom.class, path);
        assertEquals("13E", loaded.getRoomNumber());
        assertEquals(5, loaded.getMaxPeopleAllowed());
        assertEquals(2, loaded.getOccupancy());
        assertFalse(loaded.isFilled());
        assertEquals(3, loaded.getRemainingPlaces());
        assertEquals(van.getRegistrationPlate(), loaded.getAmbulanceVehicles().get(0).getRegistrationPlate());
    }
}
