package validators;

import annotations.NotNull;
import annotations.ValidDate;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ValidDateValidator implements Validator {
    public void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(ValidDate.class)) {
                    field.setAccessible(true);
                    Object value = field.get(object);

                    if (value == null && field.isAnnotationPresent(NotNull.class)) {
                        throw new IllegalArgumentException("[ERROR] " + field.getName() + " is required");
                    }

                    ValidDate annotation = field.getAnnotation(ValidDate.class);
                    ValidDate.Mode mode = annotation.value();
                    String message = mode == ValidDate.Mode.PAST ? "Date of birth cannot be a future date" : field.getName() + " cannot have a past date";

                    if(value.getClass() == Date.class) {
                        LocalDate date = ((Date) value).toLocalDate();
                        LocalDate today = LocalDate.now();

                        boolean ok =
                                (mode == ValidDate.Mode.PAST && date.isBefore(today))
                                        || (mode == ValidDate.Mode.FUTURE && date.isAfter(today));

                        if (!ok) throw new IllegalArgumentException("[ERROR] " + message);
                    }
                    else if(value.getClass() == Timestamp.class) {
                        LocalDateTime date = ((Timestamp) value).toLocalDateTime();
                        LocalDateTime today = LocalDateTime.now();

                        boolean ok =
                                (mode == ValidDate.Mode.PAST && date.isBefore(today))
                                ||  (mode == ValidDate.Mode.FUTURE && date.isAfter(today));

                        if (!ok) throw new IllegalArgumentException("[ERROR] " + message);
                    }
                    else throw new IllegalArgumentException("[ERROR] " + field.getName() + "cannot be of type " + value.getClass() + "!");
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
