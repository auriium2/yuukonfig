package com.superyuuki.yuukonfig.error;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class IllegalAccessFailure extends DeveloperFailure {

    public IllegalAccessFailure(Throwable cause) {
        super("An IllegalAccess Exception was thrown while trying to serialize the config!", cause);
    }
}
