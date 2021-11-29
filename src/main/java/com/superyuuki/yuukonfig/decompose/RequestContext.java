package com.superyuuki.yuukonfig.decompose;

public interface RequestContext<T> {

    Class<? extends T> requestedClass();
    String keyDisplayName();

}
