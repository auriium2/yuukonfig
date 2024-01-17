package yuukonfig.core.err;

import xyz.auriium.yuukonstants.GenericPath;

public class BadValueException extends YuuKonfigException {
    public BadValueException(String message, String solution, String config, GenericPath path) {
        super("badValue", String.format("in config [%s] at [%s], %s", config, path.tablePath(), message), solution);
    }

    public BadValueException(String message, String solution, String config, Throwable cause, GenericPath path) {
        super("badValue", String.format("in config [%s] at [%s], %s", config, path.tablePath(), message), cause, solution);
    }

    /* public BadValueException(String conf, String key, String message) {
        super(String.format("Error while parsing config: %s key: %s, %s", conf, key, message));
    }

    public BadValueException(String conf, String key, Throwable throwable) {
        super(String.format("While parsing config: %s key: %s exception was thrown", conf, key), throwable);
    }*/


}
