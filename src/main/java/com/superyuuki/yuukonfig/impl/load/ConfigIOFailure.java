package com.superyuuki.yuukonfig.impl.load;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class ConfigIOFailure extends DeveloperFailure {

    public ConfigIOFailure(String file, Throwable cause) {
        super(String.format("Something went wrong loading a config titled: %s, an IO exception was thrown.", file), cause);
    }
}
