package validators;

import annotations.Max;
import exceptions.IllegalTypeException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
                    double max = annotation.value();

                    if (fieldType.isAssignableFrom(String.class)) if (value.toString().length() > max) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + (int) max);
                    else if(fieldType.isAssignableFrom(Double.class)) if((double) value > max) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + max);
                    else if(fieldType.isAssignableFrom(Integer.class)) if((int) value > max) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + (int) max);
                    else if(fieldType.isAssignableFrom(ArrayList.class)) if(((ArrayList) value).size() > max) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message() + " " + (int) max);
                    else throw new IllegalTypeException("[ERROR] " + fieldType.getName() + " is an invalid type for @Max");
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
