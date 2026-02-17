package se.iths.martin.javaverktygprojekt.exceptions;

public class CustomerValidationException extends RuntimeException {
    public CustomerValidationException(String message) {
        super(message);
    }
}
