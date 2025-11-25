import com.fasterxml.jackson.databind.ObjectMapper;
import constants.PathConstants;
import models.Floor;
import models.Specialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
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

    @Test
    void testErrors() {

        assertThrows(IllegalArgumentException.class, () -> {
            Specialization s = new Specialization(
                    null,
                    new ArrayList<>(List.of("medical degree"))
            );
            ValidatorService.validate(s);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Specialization s = new Specialization(
                    "",
                    new ArrayList<>(List.of("medical degree"))
            );
            ValidatorService.validate(s);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Specialization s = new Specialization(
                    "Neurology",
                    null
            );
            ValidatorService.validate(s);
        });

        ArrayList<String> listWithNull = new ArrayList<>();
        listWithNull.add("degree");
        listWithNull.add(null);
        Specialization specializationWithNullRequirement = new Specialization(
                "Neurology", listWithNull);
        assertDoesNotThrow(() -> ValidatorService.validate(specializationWithNullRequirement));

        ArrayList<String> listWithEmpty = new ArrayList<>();
        listWithEmpty.add("degree");
        listWithEmpty.add("");
        Specialization specializationWithEmptyRequirement = new Specialization(
                "Neurology", listWithEmpty);
        assertDoesNotThrow(() -> ValidatorService.validate(specializationWithEmptyRequirement));
    }

    @Test
    void testSerialization() {
        ObjectMapper mapper = new ObjectMapper();
        String path = PathConstants.SPECIALIZATIONS_TESTS;

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(path + "test-specialization.json"), specialization);

            Specialization loaded = mapper.readValue(
                    new File(path + "test-specialization.json"),
                    Specialization.class
            );

            assertEquals("Neurology", loaded.getName());
            assertArrayEquals(
                    new String[]{"medical degree", "proficiency with MRI", "hospital internship"},
                    loaded.getRequirements().toArray()
            );

        } catch (IOException e) {
            System.err.println("[ERROR] Reading data failed:\n" + e.getMessage());
        }
    }

}
