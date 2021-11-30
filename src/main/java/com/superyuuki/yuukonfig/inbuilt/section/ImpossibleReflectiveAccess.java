package com.superyuuki.yuukonfig.inbuilt.section;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class ImpossibleReflectiveAccess extends DeveloperFailure {

    public ImpossibleReflectiveAccess(String message) {
        super(String.format("The config interface %s must be public for YuuKonfig to read it!", message));
    }
}
