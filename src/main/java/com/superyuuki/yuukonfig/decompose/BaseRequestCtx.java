package com.superyuuki.yuukonfig.decompose;

public record BaseRequestCtx<T>(Class<? extends T> requestedClass, String keyName) implements RequestContext<T> {

    @Override
    public String keyDisplayName() {
        return keyName;
    }
}
