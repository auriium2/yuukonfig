package com.superyuuki.yuukonfig.serializer;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.user.Section;
import com.superyuuki.yuukonfig.user.ConfKey;
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

        YamlNode node = YuuKonfig.instance().test().serializeTest(DefaultObjectConfig.class);

        Assertions.assertEquals("yuukonfig is better", node.asMapping().string("get_trolled_a248"));
    }

}
