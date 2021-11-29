package com.superyuuki.yuukonfig.error;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class InvocationTargetFailure extends DeveloperFailure {

    public InvocationTargetFailure(Throwable cause) {
        super("An InvocationTarget Exception was thrown while trying to serialize the config!", cause);
    }
}
