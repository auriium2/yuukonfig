package yuukonfig.core.err;


import xyz.auriium.yuukonstants.exception.ExplainedException;

public class YuuKonfigException extends ExplainedException {

    public YuuKonfigException(String type, String message, String solution) {
        super("yuukonfig", type, message, solution);
    }

    public YuuKonfigException(String type, String message, Throwable cause, String solution) {
        super("yuukonfig", type, message, cause, solution);
    }
}
