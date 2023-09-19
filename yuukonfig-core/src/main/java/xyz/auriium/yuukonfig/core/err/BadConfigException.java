package xyz.auriium.yuukonfig.core.err;

public class BadConfigException extends RuntimeException {

    public BadConfigException() {
    }

    public BadConfigException(String message) {
        super(message);
    }

    public BadConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadConfigException(Throwable cause) {
        super(cause);
    }

    public BadConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
