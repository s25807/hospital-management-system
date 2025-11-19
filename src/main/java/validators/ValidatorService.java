package validators;

public class ValidatorService {
    public static void validate(Object object) throws Exception {
        for (Validator v : ValidatorRegistry.getValidators()) v.validate(object);
    }
}
