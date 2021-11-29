package com.superyuuki.yuukonfig.compose;

import com.superyuuki.yuukonfig.error.IllegalAccessFailure;
import com.superyuuki.yuukonfig.error.InvocationTargetFailure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvocationForwarder {

    private final Method method;
    private final Object toInvokeOn;

    public InvocationForwarder(Method method, Object toInvokeOn) {
        this.method = method;
        this.toInvokeOn = toInvokeOn;
    }

    public Object invoke() {
        try {
            return method.invoke(toInvokeOn);
        } catch (IllegalAccessException e) {
            throw new IllegalAccessFailure(e);
        } catch (InvocationTargetException e) {
            throw new InvocationTargetFailure(e);
        }
    }
}
