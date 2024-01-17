package yuukonfig.core;

import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonstants.GenericPath;

import java.io.IOException;

public class TestHelp {

    final BaseManipulation manipulation;
    final RawNodeFactory factory;

    public TestHelp(BaseManipulation manipulation, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.factory = factory;
    }

    public Node serializeTest(Class<?> clazz) {
        return manipulation.serializeDefaultCtx(clazz, new GenericPath());
    }

    public <C> C deserializeTest(String config, Class<C> clazz) throws IOException {

        return clazz.cast(manipulation.deserialize(
                factory.loadString(config), //Yaml.createYamlInput(config).readYamlMapping()
                clazz
        ));
    }
}
