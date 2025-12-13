package exceptions;

public class NumberOverflowException extends RuntimeException {
    public NumberOverflowException(String message) {
        super(message);
    }
}
