package xyz.auriium.yuukonfig.core.impl;

import xyz.auriium.yuukonfig.core.TestHelp;
import xyz.auriium.yuukonfig.core.manipulation.Manipulation;
import xyz.auriium.yuukonfig.core.node.Node;
import xyz.auriium.yuukonfig.core.node.RawNodeFactory;

import java.io.IOException;

public class BaseTestHelp implements TestHelp {

    final Manipulation manipulation;
    final RawNodeFactory factory;

    public BaseTestHelp(Manipulation manipulation, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.factory = factory;
    }

    @Override
    public Node serializeTest(Class<?> clazz) {
        return manipulation.serializeDefault(clazz, new String[]{});
    }

    @Override
    public <C> C deserializeTest(String config, Class<C> clazz) throws IOException {

        return clazz.cast(manipulation.deserialize(
                factory.loadString(config), //Yaml.createYamlInput(config).readYamlMapping()
                "virtual",
                clazz
        ));
    }
}
