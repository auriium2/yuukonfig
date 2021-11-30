package com.superyuuki.yuukonfig;

import com.superyuuki.yuukonfig.impl.hold.NoHeldConfigFailure;

public interface ConfigHolder<C> {

    C current() throws NoHeldConfigFailure;
    C currentOrLoad();
    void reload();

}
