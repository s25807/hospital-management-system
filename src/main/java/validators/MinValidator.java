package validators;

import annotations.Min;

import java.lang.reflect.Field;

public class MinValidator implements Validator {
    public void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Min.class)) {
                    field.setAccessible(true);
                    Object value = field.get(object);

                    Min annotation = field.getAnnotation(Min.class);
                    double min = annotation.value();

                    if ((double) value < min) {
                        throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + min);
                    }
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
