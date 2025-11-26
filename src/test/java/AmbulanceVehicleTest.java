import models.AmbulanceVehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
class AmbulanceVehicleTest {

    static class TestAmbulance extends AmbulanceVehicle {
        public TestAmbulance(
                String registrationPlate,
                Brand brand,
                double weightLimit,
                int personLimit,
                boolean isOnMission,
                double maxSpeed,
                double rangeOfTravel
        ) {
            super(registrationPlate, brand, weightLimit, personLimit, isOnMission, maxSpeed, rangeOfTravel);
        }
    }

    @Test
    void testAmbulanceVehicleCreation() {
        AmbulanceVehicle ambulance = new TestAmbulance(
                "AB-123",
                AmbulanceVehicle.Brand.REV,
                2.5,
                4,
                true,
                130.0,
                500.0
        );

        assertEquals("AB-123", ambulance.getRegistrationPlate());
        assertEquals(AmbulanceVehicle.Brand.REV, ambulance.getBrand());
        assertEquals(2.5, ambulance.getWeightLimit());
        assertEquals(4, ambulance.getPersonLimit());
        assertTrue(ambulance.isOnMission());
        assertEquals(130.0, ambulance.getMaxSpeed());
        assertEquals(500.0, ambulance.getRangeOfTravel());
    }

    @Test
    void testSetters() {
        AmbulanceVehicle ambulance = new TestAmbulance(
                "AB-123",
                AmbulanceVehicle.Brand.REV,
                2.5,
                4,
                false,
                130.0,
                500.0
        );

        ambulance.setRegistrationPlate("XY-999");
        ambulance.setBrand(AmbulanceVehicle.Brand.ICU);
        ambulance.setWeightLimit(4.0);
        ambulance.setPersonLimit(6);
        ambulance.setOnMission(true);
        ambulance.setMaxSpeed(150.0);
        ambulance.setRangeOfTravel(700.0);

        assertEquals("XY-999", ambulance.getRegistrationPlate());
        assertEquals(AmbulanceVehicle.Brand.ICU, ambulance.getBrand());
        assertEquals(4.0, ambulance.getWeightLimit());
        assertEquals(6, ambulance.getPersonLimit());
        assertTrue(ambulance.isOnMission());
        assertEquals(150.0, ambulance.getMaxSpeed());
        assertEquals(700.0, ambulance.getRangeOfTravel());
    }
}
*/