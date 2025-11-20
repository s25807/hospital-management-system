import models.Floor;
import models.Specialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpecializationTest {
    private Specialization specialization;

    @BeforeEach
    void setup() {
        specialization = new Specialization("Neurology",
                new ArrayList<>(List.of("medical degree", "proficiency with MRI", "hospital internship")));
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Neurology", specialization.getName());
        assertArrayEquals(new String[]{"medical degree", "proficiency with MRI", "hospital internship"},
                specialization.getRequirements().toArray());
    }

    @Test
    void testSetters() {
        specialization.setName("Orthopedics");
        assertEquals("Orthopedics", specialization.getName());

        specialization.setRequirements(new ArrayList<>(List.of("stroke management training")));
        assertArrayEquals(new String[]{"stroke management training"},
                specialization.getRequirements().toArray());
    }

    @Test
    void testDefaultConstructor() {
        Specialization emptySpecialization = new Specialization();
        assertNull(emptySpecialization.getName());
        assertNull(emptySpecialization.getRequirements());
    }
}
