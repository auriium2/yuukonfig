package com.superyuuki.yuukonfig.inbuilt.section;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProxyForwarder implements Forwarder {

    private final Method method;
    private final Object toInvokeOn;

    ProxyForwarder(Method method, Object toInvokeOn) {
        this.method = method;
        this.toInvokeOn = toInvokeOn;
    }

    public Object invoke() {
        try {
            return method.invoke(toInvokeOn);
        } catch (IllegalAccessException | InvocationTargetException e) {

            if (e.getMessage().contains("cannot access a member of interface")) {
                throw new ImpossibleAccessException(
                        String.format("The config interface %s must be public for YuuKonfig to read it!", method.getDeclaringClass().getName())
                );
            }

            throw new InvocationException(
                    String.format("An exception was thrown while trying to access or serialize the config! %s", e)
            );

        }
    }

}
