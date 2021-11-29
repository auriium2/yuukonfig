package com.superyuuki.yuukonfig;

import com.superyuuki.yuukonfig.error.NoHeldConfigFailure;

import java.util.Optional;

public interface ConfigHolder<C> {

    C current() throws NoHeldConfigFailure;
    C currentOrLoad();
    void reload();

}
