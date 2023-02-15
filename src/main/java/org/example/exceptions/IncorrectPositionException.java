package org.example.exceptions;

public class IncorrectPositionException extends RuntimeException {

    public IncorrectPositionException() {
        super();
    }

    public IncorrectPositionException(String message) {
        super(message);
    }

    public IncorrectPositionException(String message, int pointY, int pointX) {
        super(message + "X: " + pointX + " Y: " + pointY);
    }

    public IncorrectPositionException(Throwable cause) {
        super(cause);
    }

}
