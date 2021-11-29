package com.superyuuki.yuukonfig.error;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class NoHandlerFailure extends DeveloperFailure {

    public NoHandlerFailure(Class<?> message) {
        super(String.format("No (de)serializer exists for type: %s", message));
    }
}
