package com.superyuuki.yuukonfig.impl;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.TestHelp;
import com.superyuuki.yuukonfig.manipulation.Manipulation;

import java.io.IOException;

public class BaseTestHelp implements TestHelp {

    private final Manipulation manipulation;

    public BaseTestHelp(Manipulation manipulation) {
        this.manipulation = manipulation;
    }

    @Override
    public YamlNode serializeTest(Class<?> clazz) {
        return manipulation.serializeDefault(clazz, new String[]{});
    }

    @Override
    public <C> C deserializeTest(String config, Class<C> clazz) throws IOException {

        return clazz.cast(manipulation.deserialize(
                Yaml.createYamlInput(config).readYamlMapping(),
                "virtual",
                clazz
        ));
    }
}
