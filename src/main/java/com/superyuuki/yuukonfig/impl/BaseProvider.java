package com.superyuuki.yuukonfig.impl;

import com.superyuuki.yuukonfig.YuuKonfigAPI;
import com.superyuuki.yuukonfig.YuuKonfigProvider;

public class BaseProvider implements YuuKonfigProvider {
    @Override
    public YuuKonfigAPI create() {
        return new BaseAPI();
    }

    @Override
    public byte priority() {
        return 0;
    }
}
