package xyz.auriium.yuukonfig.core;


import xyz.auriium.yuukonfig.core.manipulation.ManipulatorConstructor;

import java.nio.file.Path;
import java.util.List;

public interface YuuKonfigAPI {

    YuuKonfigAPI register(ManipulatorConstructor manipulator);

    List<ManipulatorConstructor> testCTORS();

    default <C> ConfigLoader<C> loader(Class<C> clazz, Path path, String fileName) {
        return loader(clazz, path.resolve(fileName));
    }

    <C> ConfigLoader<C> loader(Class<C> clazz, Path path);
    TestHelp test();

}
