package com.superyuuki.yuukonfig.deserializer;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.impl.load.BaseRegistry;
import com.superyuuki.yuukonfig.impl.request.UserRequestImpl;
import com.superyuuki.yuukonfig.inbuilt.EnumTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.IOException;

public class NestedDeserializerTest {

    private final Logger logger = LoggerFactory.getLogger(NestedDeserializerTest.class);

    static final String CONFIG = """
              
                
                number: 3
                bool: true
                nestedConfig:
                  someint: 2
                  
                """;

    @Test
    public void testNestedDeserializerValuesTrue() throws IOException {
        YamlNode node = Yaml.createYamlInput(CONFIG).readYamlMapping();

        DeserializerTestConfig config = BaseRegistry.defaults().makeDeserializers().deserializeTyped(
                node,
                new UserRequestImpl<>(DeserializerTestConfig.class),
                "virtualconfig"
        );

        Assertions.assertEquals(true, config.defaultBool());
        Assertions.assertEquals(3, config.defaultInt());
        Assertions.assertEquals(2, config.notDefaultConfig().nestedInteger());

        Assertions.assertNotEquals(4, config.defaultInt());
    }




}
