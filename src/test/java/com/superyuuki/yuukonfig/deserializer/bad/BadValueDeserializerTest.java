package com.superyuuki.yuukonfig.deserializer.bad;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.BaseRegistry;
import com.superyuuki.yuukonfig.deserializer.DeserializerTestConfig;
import com.superyuuki.yuukonfig.error.parsing.ParsingFailure;
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
    public void testBadValuesThrowsParsingException() throws IOException {

        Assertions.assertThrows(ParsingFailure.class, () -> {
            YamlNode node = Yaml.createYamlInput(BAD_VALUE_CONFIG).readYamlMapping();

            DeserializerTestConfig config = BaseRegistry.defaults().makeDeserializers().deserialize(node, DeserializerTestConfig.class, "virtualconfig");

        });


    }

}
