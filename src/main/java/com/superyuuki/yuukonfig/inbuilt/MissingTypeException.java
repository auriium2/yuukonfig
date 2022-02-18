package com.superyuuki.yuukonfig.inbuilt;


import com.superyuuki.yuukonfig.BadValueException;

public class MissingTypeException extends BadValueException {

    public MissingTypeException(String conf, String key) {
        super(conf, key, String.format("Missing a Type object during invocation to typed collection serializer for node: %s", key));
    }

}
