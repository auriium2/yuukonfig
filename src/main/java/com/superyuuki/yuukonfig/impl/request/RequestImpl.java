package com.superyuuki.yuukonfig.impl.request;

import com.superyuuki.yuukonfig.request.Request;

import java.lang.reflect.Type;
import java.util.Optional;

public class RequestImpl implements Request {

    private final Class<?> clazz;
    private final Type type; //nullable
    private final String key; //nullable

    public RequestImpl(Class<?> clazz, Type type, String key) {
        this.clazz = clazz;
        this.type = type;
        this.key = key;
    }

    public RequestImpl(Class<?> clazz, String key) {
        this.clazz = clazz;
        this.key = key;
        this.type = null;
    }

    public RequestImpl(Class<?> clazz) {
        this.clazz = clazz;
        this.key = null;
        this.type = null;
    }
    
    @Override
    public Class<?> requestedClass() {
        return clazz;
    }

    @Override
    public Optional<Type> requestedType() {
        return Optional.ofNullable(type);
    }

    @Override
    public Optional<String> keyDisplayName() {
        return Optional.ofNullable(key);
    }
}
