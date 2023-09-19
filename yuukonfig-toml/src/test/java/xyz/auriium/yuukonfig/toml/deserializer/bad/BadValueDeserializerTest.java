package xyz.auriium.yuukonfig.toml.deserializer.bad;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.auriium.yuukonfig.core.YuuKonfig;
import xyz.auriium.yuukonfig.core.err.BadValueException;
import xyz.auriium.yuukonfig.toml.deserializer.DeserializerTestConfig;

public class BadValueDeserializerTest {

    public static final String BAD_VALUE_CONFIG = """
            
            number = "dog"
            bool = 3
            
            [nestedConfig]
            someint = "hello"
            
            
            """;

    @Test
    public void testBadValuesThrowsParsingException() {

        Assertions.assertThrows(BadValueException.class, () -> {
            YuuKonfig.instance().test().deserializeTest(BAD_VALUE_CONFIG, DeserializerTestConfig.class);
        });
    }

}
