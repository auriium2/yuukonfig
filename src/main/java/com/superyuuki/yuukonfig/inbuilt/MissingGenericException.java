package com.superyuuki.yuukonfig.inbuilt;

import com.superyuuki.yuukonfig.BadValueException;

public class MissingGenericException extends BadValueException {

    public MissingGenericException(String conf, String key, int quantity) {
        super(conf, key, String.format("Collection expected %s type parameters but less than that were defined!", quantity));
    }

}
