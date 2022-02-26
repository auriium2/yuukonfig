package com.superyuuki.yuukonfig.serializer;

import com.superyuuki.yuukonfig.BaseProvider;
import com.superyuuki.yuukonfig.YuuKonfigAPI;
import com.superyuuki.yuukonfig.YuuKonfigProvider;

public class ExtendProvider implements YuuKonfigProvider {


    @Override
    public YuuKonfigAPI create() {
        return new BaseProvider()
                .create()
                .register(SerializerFallbackTest.DTOManipulator::new);
    }

    @Override
    public byte priority() {
        return 1;
    }
}
