package validators;

import annotations.Password;
import exceptions.InvalidPasswordException;

import java.lang.reflect.Field;

public class PasswordValidator implements Validator {
    private final char lower_start = 'a', lower_end = 'z', upper_start = 'A', upper_end = 'Z';
    private final char first = '0', last = '9';
    private final char[] list = {'/', '+', '-', ':', '*'};


    public void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        while (clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if(field.isAnnotationPresent(Password.class)) {
                    field.setAccessible(true);
                    Object value = field.get(object);

                    if (value instanceof String myString) {
                        if (!hasDigit(myString)) throw new InvalidPasswordException("[ERROR] Password must contain at least one digit");
                        if (!hasCapitalLetter(myString)) throw new InvalidPasswordException("[ERROR] Password must contain a capital letter");
                        if (!hasLowercaseLetter(myString)) throw new InvalidPasswordException("[ERROR] Password must contain a lower case letter");
                        if (!hasSpecialChar(myString)) throw new InvalidPasswordException("[ERROR] Password must contain a special character");
                    }
                }
            }

            clazz = clazz.getSuperclass();
        }
    }

    private boolean hasDigit(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) return true;
        }
        return false;
    }

    private boolean hasLowercaseLetter(String password) {
        for (int i = 0; i < password.length(); i++) if (Character.isLowerCase(password.charAt(i))) return true;
        return false;
    }

    private boolean hasCapitalLetter(String password) {
        for (int i = 0; i < password.length(); i++) if (Character.isUpperCase(password.charAt(i))) return true;
        return false;
    }

    private boolean hasSpecialChar(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) return true;
        }
        return false;
    }
}
