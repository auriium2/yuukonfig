package com.superyuuki.yuukonfig.deserializer.bad;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.amihaiemil.eoyaml.YamlNodeNotFoundException;
import com.superyuuki.yuukonfig.deserializer.DeserializerTestConfig;
import com.superyuuki.yuukonfig.impl.load.BaseRegistry;
import com.superyuuki.yuukonfig.impl.request.UserRequestImpl;
import com.superyuuki.yuukonfig.inbuilt.EnumTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MissingValueDeserializerTest {

    //missing "bool"
    static final String MISSING_VALUE_CONFIG = """
                
                number: 3
                nestedConfig:
                  someint: 2
                  
                """;

    @Test
    public void testBadFormatThrowsYamlException() {
        Assertions.assertThrows(YamlNodeNotFoundException.class, () -> {
            YamlNode node = Yaml.createYamlInput(MISSING_VALUE_CONFIG).readYamlMapping();

            DeserializerTestConfig config = BaseRegistry.defaults().makeDeserializers().deserializeTyped(
                    node,
                    new UserRequestImpl<>(DeserializerTestConfig.class),
                    "virtualconfig"
            );
        });


    }

}
