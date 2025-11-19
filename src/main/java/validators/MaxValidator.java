package validators;

import annotations.Max;

import java.lang.reflect.Field;

public class MaxValidator implements Validator{
    public void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Max.class)) {
                    field.setAccessible(true);
                    Object value = field.get(object);

                    Max annotation = field.getAnnotation(Max.class);
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
