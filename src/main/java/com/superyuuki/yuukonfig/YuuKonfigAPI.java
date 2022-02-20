package com.superyuuki.yuukonfig;

import com.superyuuki.yuukonfig.loader.ConfigHolder;
import com.superyuuki.yuukonfig.loader.ConfigLoader;
import com.superyuuki.yuukonfig.manipulation.ManipulatorConstructor;

import java.nio.file.Path;
import java.util.List;

public interface YuuKonfigAPI {

    YuuKonfigAPI register(ManipulatorConstructor manipulator);

    List<ManipulatorConstructor> testCTORS();

    default <T> ConfigLoader<T> loader(Class<T> clazz, Path path, String fileName) {
        return loader(clazz, path.resolve(fileName));
    }

    <T> ConfigLoader<T> loader(Class<T> clazz, Path path);
    <T> ConfigHolder<T> holder(Class<T> clazz);

    TestHelp test();

}
