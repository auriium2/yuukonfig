package com.superyuuki.yuukonfig.error;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class NoHeldConfigFailure extends DeveloperFailure {

    public NoHeldConfigFailure() {
        super("Code tried to get a config instance when no config was loaded into a config holder!");
    }
}
