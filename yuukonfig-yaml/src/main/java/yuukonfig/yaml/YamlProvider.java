package yuukonfig.yaml;

import yuukonfig.core.YuuKonfigAPI;
import yuukonfig.core.YuuKonfigProvider;
import yuukonfig.core.impl.BaseAPI;
import yuukonfig.core.impl.manipulator.*;
import yuukonfig.core.impl.manipulator.section.SectionManipulator;

public class YamlProvider implements YuuKonfigProvider {
    @Override
    public YuuKonfigAPI create() {
        return new BaseAPI(new YamlNodeFactory())
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
