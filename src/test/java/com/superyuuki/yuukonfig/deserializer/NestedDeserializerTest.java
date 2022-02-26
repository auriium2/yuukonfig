package com.superyuuki.yuukonfig.deserializer;

import com.superyuuki.yuukonfig.YuuKonfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class NestedDeserializerTest {

    static final String CONFIG = """
              
                
                number: 3
                bool: true
                nestedConfig:
                  someint: 2
                myUUID: wdawdawda
                  
                """;

    @Test
    public void testNestedDeserializerValuesTrue() throws IOException {
        DeserializerTestConfig config = YuuKonfig.instance().test().deserializeTest(CONFIG, DeserializerTestConfig.class);

        Assertions.assertEquals(true, config.defaultBool());
        Assertions.assertEquals(3, config.defaultInt());
        Assertions.assertEquals(2, config.notDefaultConfig().nestedInteger());

        Assertions.assertNotEquals(4, config.defaultInt());
    }




}
