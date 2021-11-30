package com.superyuuki.yuukonfig.inbuilt.collection;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class MissingTypeFailure extends DeveloperFailure {

    public MissingTypeFailure(String message) {
        super(String.format("Missing a Type object during invocation to typed collection serializer for node: %s", message));
    }
}
