package org.example.exceptions;

public class IncorrectMeasurementsException extends RuntimeException {

    public IncorrectMeasurementsException() {
        super();
    }

    public IncorrectMeasurementsException(String message) {
        super(message);
    }

    public IncorrectMeasurementsException(Throwable cause) {
        super(cause);
    }
}
