package com.superyuuki.yuukonfig.error.parsing;

import com.superyuuki.yuukonstants.Failure;

public class ParsingFailure extends Failure {

    public ParsingFailure() {
    }

    public ParsingFailure(String message) {
        super(message);
    }

    public ParsingFailure(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingFailure(Throwable cause) {
        super(cause);
    }

    public ParsingFailure(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
