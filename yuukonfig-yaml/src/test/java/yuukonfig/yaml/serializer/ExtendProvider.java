package yuukonfig.yaml.serializer;

import yuukonfig.core.YuuKonfigAPI;
import yuukonfig.core.YuuKonfigProvider;
import yuukonfig.yaml.YamlProvider;

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
