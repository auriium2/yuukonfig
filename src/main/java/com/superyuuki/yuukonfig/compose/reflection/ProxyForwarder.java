package com.superyuuki.yuukonfig.compose.reflection;

import com.superyuuki.yuukonfig.error.IllegalAccessFailure;
import com.superyuuki.yuukonfig.error.InvocationTargetFailure;

import java.lang.invoke.MethodHandles;
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
            throw new IllegalAccessFailure(e);
        } catch (Throwable e) {
            throw new InvocationTargetFailure(e);
        }
    }

}
