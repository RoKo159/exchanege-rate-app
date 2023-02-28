package pl.kurs.exchangerateapponspring.exceptions;

public class NoConnectionException extends Exception {
    public NoConnectionException(String message) {
        super(message);
    }

    public NoConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
