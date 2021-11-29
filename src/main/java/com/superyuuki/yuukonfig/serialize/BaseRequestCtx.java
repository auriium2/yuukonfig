package com.superyuuki.yuukonfig.serialize;

import com.superyuuki.yuukonfig.serialize.RequestContext;

import java.lang.reflect.Type;

public record BaseRequestCtx<T>(Class<? extends T> requestedClass, Type type, String keyName) implements RequestContext<T> {


    @Override
    public Type requestedType() {
        return type;
    }

    @Override
    public String keyDisplayName() {
        return keyName;
    }
}
