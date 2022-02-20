package com.superyuuki.yuukonfig.deserializer.bad;

import com.amihaiemil.eoyaml.YamlNodeNotFoundException;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.deserializer.DeserializerTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
            DeserializerTestConfig config = YuuKonfig.instance().test().deserializeTest(MISSING_VALUE_CONFIG, DeserializerTestConfig.class);

            config.defaultBool();
        });


    }

}
