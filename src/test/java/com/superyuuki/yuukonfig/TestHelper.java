package com.superyuuki.yuukonfig;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.impl.load.BaseRegistry;
import com.superyuuki.yuukonfig.impl.request.RequestImpl;
import com.superyuuki.yuukonfig.impl.request.UserRequestImpl;
import com.superyuuki.yuukonfig.inbuilt.EnumTest;

import java.io.IOException;

public class TestHelper {

    public static YamlNode serializerTest(Class<?> testConfig) {
        return BaseRegistry.defaults().makeSerializers().serializeDefault(
                new RequestImpl(testConfig)
        );
    }

    public static <C> C deserializerTest(String fakeConfig, Class<C> testConfig) {
        try {
            return BaseRegistry.defaults().makeDeserializers().deserializeTyped(
                    Yaml.createYamlInput(fakeConfig).readYamlMapping(),
                    new UserRequestImpl<>(testConfig),
                    "helper-config-virtual"
            );
        } catch (IOException e) {
            throw new IllegalStateException("Threw IOException during testing!", e);
        }

    }

}
