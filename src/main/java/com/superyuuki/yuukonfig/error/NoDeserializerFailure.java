package com.superyuuki.yuukonfig.error;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class NoDeserializerFailure extends DeveloperFailure {

    public NoDeserializerFailure(Class<?> message) {
        super(String.format("No deserializer exists for a value defined by developer in config type: %s", message));
    }

}
