package validators;

import annotations.NotNull;
import java.lang.reflect.Field;

public class NotNullValidator implements Validator {
    public void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(NotNull.class)) {
                    field.setAccessible(true);
                    Object value = field.get(object);

                    if (value == null) {
                        NotNull annotation = field.getAnnotation(NotNull.class);
                        throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message());
                    }
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
