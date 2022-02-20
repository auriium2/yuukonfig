package com.superyuuki.yuukonfig.deserializer.bad;

import com.superyuuki.yuukonfig.BadValueException;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.deserializer.DeserializerTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BadValueDeserializerTest {

    public static final String BAD_VALUE_CONFIG = """
            
            number: 'dog'
            bool: '3'
            nestedConfig:
              someint: 'hello'
            
            
            """;


    @Test
    public void testNothing() {
        System.out.println("hi");
    }


    @Test
    public void testBadValuesThrowsParsingException() {

        Assertions.assertThrows(BadValueException.class, () -> {
            YuuKonfig.instance().test().deserializeTest(BAD_VALUE_CONFIG, DeserializerTestConfig.class);
        });
    }

}
