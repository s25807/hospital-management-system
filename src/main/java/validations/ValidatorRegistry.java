package validations;

import java.util.List;

public class ValidatorRegistry {
    public static final List<Validator> validators = List.of(
            new NotNullValidator(),
            new ValidDateValidator()
    );

    public static List<Validator> getValidators() { return validators; }
}
