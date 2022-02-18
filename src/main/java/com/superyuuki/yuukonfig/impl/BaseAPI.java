package com.superyuuki.yuukonfig.impl;

import com.superyuuki.yuukonfig.YuuKonfigAPI;
import com.superyuuki.yuukonfig.loader.ConfigHolder;
import com.superyuuki.yuukonfig.loader.ConfigLoader;
import com.superyuuki.yuukonfig.manipulation.ManipulatorConstructor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BaseAPI implements YuuKonfigAPI {

    private final List<ManipulatorConstructor> ctors = new ArrayList<>();

    @Override
    public void register(ManipulatorConstructor manipulator) {
        ctors.add(manipulator);
    }

    @Override
    public <T> ConfigLoader<T> loader(Class<T> clazz, Path configPath) {
        return new BaseLoader<>(
                new BaseManipulation(List.copyOf(ctors), configPath.toString()),
                clazz,
                configPath
        );
    }

    @Override
    public <T> ConfigHolder<T> holder(Class<T> clazz) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
