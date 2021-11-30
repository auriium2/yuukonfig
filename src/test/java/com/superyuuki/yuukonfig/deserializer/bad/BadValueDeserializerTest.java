package com.superyuuki.yuukonfig.deserializer.bad;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.deserializer.DeserializerTestConfig;
import com.superyuuki.yuukonfig.impl.decompose.ParsingFailure;
import com.superyuuki.yuukonfig.impl.load.BaseRegistry;
import com.superyuuki.yuukonfig.impl.request.UserRequestImpl;
import com.superyuuki.yuukonfig.inbuilt.EnumTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BadValueDeserializerTest {

    static final String BAD_VALUE_CONFIG = """
                
                number: help
                bool: true
                nestedConfig:
                  someint: 2
                  
                """;

    @Test
    public void testBadValuesThrowsParsingException() {

        Assertions.assertThrows(ParsingFailure.class, () -> {
            YamlNode node = Yaml.createYamlInput(BAD_VALUE_CONFIG).readYamlMapping();

            DeserializerTestConfig config = BaseRegistry.defaults().makeDeserializers().deserializeTyped(
                    node,
                    new UserRequestImpl<>(DeserializerTestConfig.class),
                    "virtualconfig"
            );
        });


    }

}
