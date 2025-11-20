package validators;

import annotations.Length;

import java.lang.reflect.Field;
import java.text.MessageFormat;

public class LengthValidator implements Validator {
    public void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Length.class)) {
                    field.setAccessible(true);
                    Object value = field.get(object);

                    Length annotation = field.getAnnotation(Length.class);
                    int requiredLength = annotation.value();
                    int actualLength = value.toString().length();

                    if (actualLength != requiredLength) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + MessageFormat.format(annotation.message(), requiredLength, actualLength));
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
