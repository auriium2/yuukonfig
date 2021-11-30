package com.superyuuki.yuukonfig.serializer;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.TestHelper;
import com.superyuuki.yuukonfig.annotate.ConfKey;
import com.superyuuki.yuukonfig.impl.load.BaseRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultObjectSerializedTest {

    public interface DefaultObjectConfig extends Section {

        @ConfKey("get_trolled_a248")
        default String someValue() {
            return "yuukonfig is better";
        }

    }

    @Test
    public void testDefaultObjectCanBeSerialized() {

        YamlNode node = TestHelper.serializerTest(DefaultObjectConfig.class);

        Assertions.assertEquals("yuukonfig is better", node.asMapping().string("get_trolled_a248"));
    }

}
