import constants.PathConstants;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ObjectStore;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientAmbulanceTransitTest {
    private final ObjectStore objectStore = new ObjectStore();
    private PatientAmbulanceTransit patientAmbulanceTransitOne;
    private PatientAmbulanceTransit patientAmbulanceTransitTwo;
    private Patient patient;
    private Boat boat;
    private Van van;

    @BeforeEach
    public void setUp() {
        patient = new Patient(
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

        boat = new Boat(
                "AZZ-324",
                AmbulanceVehicle.Brand.ICU,
                2500,
                6,
                false,
                250,
                1000,
                700
        );

        patientAmbulanceTransitOne = new PatientAmbulanceTransit(PatientAmbulanceTransit.Status.HEADING_TO_PATIENT, Timestamp.valueOf("2023-05-05 13:40:14"), "Warsaw, Zlota 22", van, List.of(patient));
        patientAmbulanceTransitTwo = new PatientAmbulanceTransit(PatientAmbulanceTransit.Status.ARRIVED, Timestamp.valueOf("2021-12-29 23:23:59"), "Gdańsk, Kubusia Puchata 49/32", boat, List.of(patient));
    }

    @Test
    void testConstructors() {
        assertTrue(patient.getPatientAmbulanceTransitList().contains(patientAmbulanceTransitOne));
        assertEquals(PatientAmbulanceTransit.Status.HEADING_TO_PATIENT, patientAmbulanceTransitOne.getStatus());
        assertEquals(Timestamp.valueOf("2023-05-05 13:40:14"), patientAmbulanceTransitOne.getRegistered());
        assertEquals("Warsaw, Zlota 22", patientAmbulanceTransitOne.getPickLocation());

        assertTrue(patient.getPatientAmbulanceTransitList().contains(patientAmbulanceTransitTwo));
        assertEquals(PatientAmbulanceTransit.Status.ARRIVED, patientAmbulanceTransitTwo.getStatus());
        assertEquals(Timestamp.valueOf("2021-12-29 23:23:59"), patientAmbulanceTransitTwo.getRegistered());
        assertEquals("Gdańsk, Kubusia Puchata 49/32", patientAmbulanceTransitTwo.getPickLocation());
    }

    @Test
    void serializationTest() {
        String pathOne = PathConstants.PATIENTS_AMBULANCE_TRANSIT_TESTS + "transit-test-1.json";
        String pathTwo = PathConstants.PATIENTS_AMBULANCE_TRANSIT_TESTS + "transit-test-2.json";

        objectStore.save(patientAmbulanceTransitOne, pathOne);
        objectStore.save(patientAmbulanceTransitTwo, pathTwo);

        PatientAmbulanceTransit loadedOne = objectStore.load(PatientAmbulanceTransit.class, pathOne);
        PatientAmbulanceTransit loadedTwo = objectStore.load(PatientAmbulanceTransit.class, pathTwo);

        assertEquals(PatientAmbulanceTransit.Status.HEADING_TO_PATIENT, loadedOne.getStatus());
        assertEquals(Timestamp.valueOf("2023-05-05 13:40:14"), loadedOne.getRegistered());
        assertEquals("Warsaw, Zlota 22", loadedOne.getPickLocation());

        assertEquals(PatientAmbulanceTransit.Status.ARRIVED, loadedTwo.getStatus());
        assertEquals(Timestamp.valueOf("2021-12-29 23:23:59"), loadedTwo.getRegistered());
        assertEquals("Gdańsk, Kubusia Puchata 49/32", loadedTwo.getPickLocation());
    }
}
