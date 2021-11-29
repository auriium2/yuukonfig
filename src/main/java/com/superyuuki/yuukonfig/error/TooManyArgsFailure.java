package com.superyuuki.yuukonfig.error;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class TooManyArgsFailure extends DeveloperFailure {

    public TooManyArgsFailure(String message) {
        super(String.format("The config interface method %s cannot have arguments!", message));
    }
}
