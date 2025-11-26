package validators;

import annotations.Max;
import exceptions.IllegalTypeException;

import java.lang.reflect.Field;

public class MaxValidator implements Validator{
    public void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Max.class)) {
                    field.setAccessible(true);

                    Class<?> fieldType = field.getType();
                    Object value = field.get(object);

                    Max annotation = field.getAnnotation(Max.class);
                    double min = annotation.value();

                    if (fieldType.isAssignableFrom(String.class)) if (value.toString().length() < min) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + (int) min);
                    else if(fieldType.isAssignableFrom(Double.class)) if((double) value < min) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + min);
                    else if(fieldType.isAssignableFrom(Integer.class)) if((int) value < min) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + (int) min);
                    else throw new IllegalTypeException("[ERROR] " + fieldType.getName() + " is an invalid type for @Max");
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
