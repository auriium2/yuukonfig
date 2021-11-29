package com.superyuuki.yuukonfig.error;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class NoDefaultSerializerFailure extends DeveloperFailure {

    public NoDefaultSerializerFailure(Class<?> type) {
        super(String.format("No default value can be computed for classes of type: %s! Please add a default value to your config", type.getName()));
    }
}
