package xyz.auriium.yuukonfig.yaml;

import com.amihaiemil.eoyaml.YamlNode;
import xyz.auriium.yuukonfig.core.YuuKonfigAPI;
import xyz.auriium.yuukonfig.core.YuuKonfigProvider;
import xyz.auriium.yuukonfig.core.impl.BaseAPI;
import xyz.auriium.yuukonfig.core.impl.manipulator.*;
import xyz.auriium.yuukonfig.core.impl.manipulator.section.SectionManipulator;

public class YamlProvider implements YuuKonfigProvider {
    @Override
    public YuuKonfigAPI create() {
        return new BaseAPI(new YamlRawNodeFactory())
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
