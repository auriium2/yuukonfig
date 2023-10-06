package yuukonfig.core;

import yuukonfig.core.node.Node;

import java.io.IOException;

public interface TestHelp {

    Node serializeTest(Class<?> clazz);
    <C> C deserializeTest(String config, Class<C> clazz) throws IOException;

}
