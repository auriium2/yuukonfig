package com.superyuuki.yuukonfig.error;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class NoSerializerFailure extends DeveloperFailure {

    public NoSerializerFailure(Class<?> message) {
        super(String.format("No serializer exists for a value defined by developer in config type: %s", message));
    }
}
