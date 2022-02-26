package com.superyuuki.yuukonfig;

import com.amihaiemil.eoyaml.YamlNode;

import java.io.IOException;

public interface TestHelp {

    YamlNode serializeTest(Class<?> clazz);
    <C> C deserializeTest(String config, Class<C> clazz) throws IOException;

}
