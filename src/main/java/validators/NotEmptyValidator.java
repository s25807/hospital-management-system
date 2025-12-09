package validators;

import annotations.NotEmpty;
import exceptions.IllegalTypeException;

import java.lang.reflect.Field;
import java.util.HashMap;

public class NotEmptyValidator implements Validator {
    public void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(NotEmpty.class)) {
                    field.setAccessible(true);
                    Object value = field.get(object);

                    NotEmpty annotation = field.getAnnotation(NotEmpty.class);

                    if (value.getClass() == String.class) {
                        if (((String) value).isEmpty()) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message());
                    }
                    else if(value.getClass() == HashMap.class){
                        if (((HashMap) value).isEmpty()) throw new IllegalArgumentException("[ERROR] " + field.getName() + " " + annotation.message());
                    }
                    else throw new IllegalTypeException("[ERROR] " + field.getName() + " " + "should be of String type!");
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
