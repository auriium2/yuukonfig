package com.superyuuki.yuukonfig.request;

/**
 * Represents a request by a "user", an entity that cares about the type or requires it at compile-time
 * @param <T> type
 */
public interface UserRequest<T> extends Request {

    Class<T> typedRequestedClass();

    @Override
    default Class<?> requestedClass() {
        return typedRequestedClass();
    }
}
