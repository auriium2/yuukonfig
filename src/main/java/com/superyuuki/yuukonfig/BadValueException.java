package com.superyuuki.yuukonfig;

public class BadValueException extends BadConfigException {

    public BadValueException(String conf, String key, String message) {
        super(String.format("Error while parsing config: %s key: %s, %s", conf, key, message));
    }

    public BadValueException(String conf, String key, Throwable throwable) {
        super(String.format("While parsing config: %s key: %s exception was thrown", conf, key), throwable);
    }


}
