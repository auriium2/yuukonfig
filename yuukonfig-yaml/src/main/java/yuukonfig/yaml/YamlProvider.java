package yuukonfig.yaml;

import yuukonfig.core.YuuKonfigAPI;
import yuukonfig.core.YuuKonfigProvider;
import yuukonfig.core.impl.BaseAPI;
import yuukonfig.core.impl.manipulator.*;
import yuukonfig.core.impl.manipulator.section.SectionManipulator;
import yuukonfig.core.impl.safe.HandlesPrimitiveManipulator;
import yuukonfig.core.impl.safe.HandlesSafeManipulator;

import java.util.UUID;

public class YamlProvider implements YuuKonfigProvider {
    @Override
    public YuuKonfigAPI create() {
        return new BaseAPI(new YamlNodeFactory())
                .register(SectionManipulator::new)
                .register(HandlesPrimitiveManipulator.ofSpecific(Boolean.class, boolean.class, BooleanManipulator::new))
                .register(EnumManipulator::new)
                .register(HandlesPrimitiveManipulator.ofSpecific(Integer.class, int.class, IntManipulator::new))
                .register(StringManipulator::new)
                .register(HandlesPrimitiveManipulator.ofSpecific(Double.class, double.class, DoubleManipulator::new))
                .register(HandlesSafeManipulator.ofSpecific(UUID.class, UUIDManipulator::new))
                .register(ListManipulator::new);
    }

    @Override
    public byte priority() {
        return 0;
    }
}
