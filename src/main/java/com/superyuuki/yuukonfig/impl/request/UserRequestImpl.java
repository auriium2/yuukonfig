package com.superyuuki.yuukonfig.impl.request;

import com.superyuuki.yuukonfig.request.UserRequest;

import java.lang.reflect.Type;
import java.util.Optional;

public class UserRequestImpl<T> implements UserRequest<T> {

    private final Class<T> clazz;
    private final Type type; //nullable
    private final String key; //nullable

    public UserRequestImpl(Class<T> clazz, Type type, String key) {
        this.clazz = clazz;
        this.type = type;
        this.key = key;
    }

    public UserRequestImpl(Class<T> clazz, String key) {
        this.clazz = clazz;
        this.key = key;
        this.type = null;
    }

    public UserRequestImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.key = null;
        this.type = null;
    }

    @Override
    public Optional<Type> requestedType() {
        return Optional.ofNullable(type);
    }

    @Override
    public Optional<String> keyDisplayName() {
        return Optional.ofNullable(key);
    }

    @Override
    public Class<T> typedRequestedClass() {
        return clazz;
    }
}
