package com.superyuuki.yuukonfig;

import com.superyuuki.yuukonfig.YuuKonfigAPI;
import com.superyuuki.yuukonfig.YuuKonfigProvider;
import com.superyuuki.yuukonfig.impl.BaseAPI;
import com.superyuuki.yuukonfig.inbuilt.*;
import com.superyuuki.yuukonfig.inbuilt.section.SectionManipulator;

public class BaseProvider implements YuuKonfigProvider {
    @Override
    public YuuKonfigAPI create() {
        return new BaseAPI()
                .register(SectionManipulator::new)
                .register(BooleanManipulator::new)
                .register(EnumManipulator::new)
                .register(IntManipulator::new)
                .register(StringManipulator::new)
                .register(DoubleManipulator::new)
                .register(UUIDManipulator::new)
                .register(ListManipulator::new);
    }

    @Override
    public byte priority() {
        return 0;
    }
}
