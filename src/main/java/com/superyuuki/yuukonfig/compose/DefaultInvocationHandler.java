package com.superyuuki.yuukonfig.compose;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DefaultInvocationHandler<T> implements InvocationHandler {

    private final Class<T> target;

    DefaultInvocationHandler(Class<T> target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return MethodHandles.privateLookupIn(target, MethodHandles.lookup())
                .in(target)
                .unreflectSpecial(method, target)
                .bindTo(proxy)
                .invokeWithArguments(args);
    }
}
