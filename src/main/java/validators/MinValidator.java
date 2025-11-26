package validators;

import annotations.Min;
import exceptions.IllegalTypeException;

import java.lang.reflect.Field;

public class MinValidator implements Validator {
    public void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Min.class)) {
                    field.setAccessible(true);

                    Class<?> fieldType = field.getType();
                    Object value = field.get(object);

                    Min annotation = field.getAnnotation(Min.class);
                    double min = annotation.value();

                    if (fieldType.isAssignableFrom(String.class)) if (value.toString().length() < min) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + (int) min);
                    else if(fieldType.isAssignableFrom(Double.class)) if((double) value < min) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + min);
                    else if(fieldType.isAssignableFrom(Integer.class)) if((int) value < min) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + (int) min);
                    else throw new IllegalTypeException("[ERROR] " + fieldType.getName() + " is an invalid type for @Min");
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
