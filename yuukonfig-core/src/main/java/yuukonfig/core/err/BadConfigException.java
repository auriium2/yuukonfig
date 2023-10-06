package yuukonfig.core.err;

import yuukonstants.exception.ExplainedException;

public class BadConfigException extends ExplainedException {

    public BadConfigException(String type, String message, String solution) {
        super("yuukonfig", type, message, solution);
    }

    public BadConfigException(String type, String message, Throwable cause, String solution) {
        super("yuukonfig", type, message, cause, solution);
    }
}
