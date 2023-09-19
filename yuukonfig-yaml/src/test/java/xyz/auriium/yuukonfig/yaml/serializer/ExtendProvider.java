package xyz.auriium.yuukonfig.yaml.serializer;

import xyz.auriium.yuukonfig.core.YuuKonfigAPI;
import xyz.auriium.yuukonfig.core.YuuKonfigProvider;
import xyz.auriium.yuukonfig.yaml.YamlProvider;

public class ExtendProvider implements YuuKonfigProvider {


    @Override
    public YuuKonfigAPI create() {
        return new YamlProvider()
                .create()
                .register(SerializerFallbackTest.DTOManipulator::new);
    }

    @Override
    public byte priority() {
        return 1;
    }
}
