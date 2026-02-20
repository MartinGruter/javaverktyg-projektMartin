package se.iths.martin.javaverktygprojekt.exceptions;

public class ReviewValidationException extends RuntimeException {
    public ReviewValidationException(String message) {
        super(message);
    }
}
