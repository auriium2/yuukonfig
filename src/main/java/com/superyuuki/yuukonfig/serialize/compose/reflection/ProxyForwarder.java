package com.superyuuki.yuukonfig.serialize.compose.reflection;

import com.superyuuki.yuukonfig.error.IllegalAccessFailure;
import com.superyuuki.yuukonfig.error.ImpossibleReflectiveAccess;
import com.superyuuki.yuukonfig.error.InvocationTargetFailure;

import java.lang.reflect.Method;

public class ProxyForwarder implements Forwarder {

    private final Method method;
    private final Object toInvokeOn;

    public ProxyForwarder(Method method, Object toInvokeOn) {
        this.method = method;
        this.toInvokeOn = toInvokeOn;
    }

    public Object invoke() {
        try {
            return method.invoke(toInvokeOn);
        } catch (IllegalAccessException e) {

            if (e.getMessage().contains("cannot access a member of interface")) {
                throw new ImpossibleReflectiveAccess(method.getDeclaringClass().getName());
            }

            throw new IllegalAccessFailure(e);
        } catch (Throwable e) {
            throw new InvocationTargetFailure(e);
        }
    }

}
