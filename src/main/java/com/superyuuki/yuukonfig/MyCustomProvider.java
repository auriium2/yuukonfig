package com.superyuuki.yuukonfig;

public class MyCustomProvider implements YuuKonfigProvider {
    @Override
    public YuuKonfigAPI create() {
        return new BaseProvider().create()
                .register(null); //add custpm
    }

    @Override
    public byte priority() {
        return 1;
    }
}
