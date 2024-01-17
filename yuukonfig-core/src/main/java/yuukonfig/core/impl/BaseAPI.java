package yuukonfig.core.impl;

import yuukonfig.core.ConfigLoader;
import yuukonfig.core.TestHelp;
import yuukonfig.core.YuuKonfigAPI;
import yuukonfig.core.manipulation.ManipulatorConstructor;
import yuukonfig.core.node.RawNodeFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BaseAPI implements YuuKonfigAPI {

    final RawNodeFactory factory;
    final List<ManipulatorConstructor> ctors = new ArrayList<>();

    public BaseAPI(RawNodeFactory factory) {
        this.factory = factory;
    }

    @Override
    public YuuKonfigAPI register(ManipulatorConstructor manipulator) {
        ctors.add(manipulator);

        return this;
    }

    @Override
    public List<ManipulatorConstructor> testCTORS() {
       return this.ctors;
    }

    @Override
    public <C> ConfigLoader<C> loader(Class<C> clazz, Path configPath) {
        return new ConfigLoader<>(
                factory, new BaseManipulation(factory, ctors.toArray(ManipulatorConstructor[]::new), configPath.getFileName().toString(), configPath.toString()),
                clazz,
                configPath
        );
    }

    @Override
    public TestHelp test() {
        return new TestHelp(new BaseManipulation(factory, ctors.toArray(ManipulatorConstructor[]::new), "virtual", "virtual"), factory);
    }
}
