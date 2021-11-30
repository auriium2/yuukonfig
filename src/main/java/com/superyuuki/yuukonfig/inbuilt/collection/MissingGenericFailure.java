package com.superyuuki.yuukonfig.inbuilt.collection;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class MissingGenericFailure extends DeveloperFailure {

    public MissingGenericFailure(int expectedLength, String key) {
        super(String.format("A collection defined in config node: %s expected %s type parameters but less than that were defined!", key, expectedLength));
    }
}
