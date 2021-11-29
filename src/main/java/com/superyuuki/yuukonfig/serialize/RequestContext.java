package com.superyuuki.yuukonfig.serialize;

import java.lang.reflect.Type;

public interface RequestContext<T> {

    Class<? extends T> requestedClass();
    Type requestedType();
    String keyDisplayName();

}
