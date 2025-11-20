package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,  ElementType.PARAMETER})
public @interface Length {
    int value() default 10;
    String message() default "has to be exactly {0} characters long";
}
