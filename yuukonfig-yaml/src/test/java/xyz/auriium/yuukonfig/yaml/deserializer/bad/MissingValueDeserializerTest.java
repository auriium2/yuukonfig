package xyz.auriium.yuukonfig.yaml.deserializer.bad;

import com.amihaiemil.eoyaml.YamlNodeNotFoundException;
import xyz.auriium.yuukonfig.core.YuuKonfig;
import xyz.auriium.yuukonfig.core.err.BadConfigException;
import xyz.auriium.yuukonfig.yaml.deserializer.DeserializerTestConfig;
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
        Assertions.assertThrows(BadConfigException.class, () -> {
            DeserializerTestConfig config = YuuKonfig.instance().test().deserializeTest(MISSING_VALUE_CONFIG, DeserializerTestConfig.class);

            config.defaultBool();
        });


    }

}
