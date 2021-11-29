package com.superyuuki.yuukonfig;

public interface ConfigBinder<C> {

    C proxied();
    void reload();

}
