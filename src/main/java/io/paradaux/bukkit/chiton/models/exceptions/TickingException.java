package io.paradaux.bukkit.chiton.models.exceptions;

public class TickingException extends RuntimeException {

    public TickingException() {
    }

    public TickingException(String message) {
        super(message);
    }

    public TickingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TickingException(Throwable cause) {
        super(cause);
    }

    public TickingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
