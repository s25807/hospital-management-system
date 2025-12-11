import models.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import validators.ValidatorService;

public class DepartmentTest {

    @Test
    public void gettersAndSetters() {
        Department department = new Department();
        department.setId("14");
        department.setName("Department of Neurology");

        Assertions.assertEquals("14", department.getId());
        Assertions.assertEquals("Department of Neurology", department.getName());
    }

    @Test
    public void validateDepartmentTest() {
        Department department = new Department();
        Assertions.assertThrows(NullPointerException.class, () -> {
            department.setId(null);
            ValidatorService.validate(department);
        });
    }

    //TODO Serialization Test
}
