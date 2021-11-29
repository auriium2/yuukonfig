package com.superyuuki.yuukonfig.serialize;

import java.lang.reflect.Type;

public class InitialRequestCtx<T> implements RequestContext<T> {

    private final Class<T> clazz;

    public InitialRequestCtx(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<T> requestedClass() {
        return clazz;
    }

    @Override
    public Type requestedType() {
        throw new UnsupportedOperationException("base request has no type at runtime!");
    }

    @Override
    public String keyDisplayName() {
        throw new UnsupportedOperationException("base request has no display name for key (represents config!)");
    }
}
