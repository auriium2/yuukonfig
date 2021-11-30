package com.superyuuki.yuukonfig.impl.compose;

import com.superyuuki.yuukonstants.DeveloperFailure;

//TODO make it more descriptive, show the node, show everything, etc
public class NoSerializerFailure extends DeveloperFailure {

    public NoSerializerFailure(Class<?> message) {
        super(String.format("No serializer exists for a value defined by developer in config type: %s", message));
    }
}
