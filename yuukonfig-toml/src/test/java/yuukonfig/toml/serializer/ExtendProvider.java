package yuukonfig.toml.serializer;

import yuukonfig.core.YuuKonfigAPI;
import yuukonfig.core.YuuKonfigProvider;
import yuukonfig.toml.TomlProvider;

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
