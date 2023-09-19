package xyz.auriium.yuukonfig.toml.serializer;

import xyz.auriium.yuukonfig.core.YuuKonfigAPI;
import xyz.auriium.yuukonfig.core.YuuKonfigProvider;
import xyz.auriium.yuukonfig.toml.TomlProvider;

public class ExtendProvider implements YuuKonfigProvider {


    @Override
    public YuuKonfigAPI create() {
        return new TomlProvider()
                .create()
                .register(SerializerFallbackTest.DTOManipulator::new);
    }

    @Override
    public byte priority() {
        return 1;
    }
}
