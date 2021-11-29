package com.superyuuki.yuukonfig.deserializer.bad;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.amihaiemil.eoyaml.YamlNodeNotFoundException;
import com.superyuuki.yuukonfig.BaseRegistry;
import com.superyuuki.yuukonfig.deserializer.DeserializerTestConfig;
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
    public void testBadFormatThrowsYamlException() throws IOException {
        Assertions.assertThrows(YamlNodeNotFoundException.class, () -> {
            YamlNode node = Yaml.createYamlInput(MISSING_VALUE_CONFIG).readYamlMapping();

            DeserializerTestConfig config = BaseRegistry.defaults().makeDeserializers().deserialize(node, DeserializerTestConfig.class, "virtualconfig");
        });


    }

}
