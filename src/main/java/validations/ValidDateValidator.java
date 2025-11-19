package validations;

import annotations.ValidDate;

import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDate;

public class ValidDateValidator implements Validator {
    public void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(ValidDate.class)) {
                    field.setAccessible(true);
                    Object value = field.get(object);

                    if (value == null) continue;

                    ValidDate annotation = field.getAnnotation(ValidDate.class);
                    ValidDate.Mode mode = annotation.value();
                    String message = mode == ValidDate.Mode.PAST ? "Date of birth cannot be a future date" : field.getName() + " cannot have a past date";

                    LocalDate date = ((Date) value).toLocalDate();
                    LocalDate today = LocalDate.now();

                    boolean ok =
                            (mode == ValidDate.Mode.PAST && date.isBefore(today)) ||
                            (mode == ValidDate.Mode.FUTURE && date.isAfter(today));

                    if (!ok) throw new IllegalArgumentException("[ERROR] " + message);
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
