package com.superyuuki.yuukonfig.inbuilt;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {

    static final String SOME_UUID_CONFIG = """
            
            someUUID: 8046dfc5-4ef7-4bd5-831b-05978815148f
            
            """;

    @Test
    public void testSerializingUUID() {
        YamlNode node = TestHelper.serializerTest(UUIDConfig.class);

        Assertions.assertEquals("8046dfc5-4ef7-4bd5-831b-05978815148f", node.asMapping().string("someUUID"));
    }

    public interface UUIDConfig extends Section {
        default UUID someUUID() {
            return UUID.fromString("8046dfc5-4ef7-4bd5-831b-05978815148f");
        }
    }

    @Test
    public void testDeserializingUUID() {
        UUIDConfig config = TestHelper.deserializerTest(SOME_UUID_CONFIG, UUIDConfig.class);

        Assertions.assertEquals(UUID.fromString("8046dfc5-4ef7-4bd5-831b-05978815148f"), config.someUUID());
    }

}
