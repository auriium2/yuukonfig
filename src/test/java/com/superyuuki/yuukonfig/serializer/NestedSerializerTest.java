package com.superyuuki.yuukonfig.serializer;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.annotate.ConfKey;
import com.superyuuki.yuukonfig.BaseRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NestedSerializerTest {

    public interface InternalConfig extends Section {

        @ConfKey("number")
        default Integer defaultInt() {
            return 5;
        }

        @ConfKey("bool")
        default Boolean defaultBool() {
            return true;
        }

        @ConfKey("nestedConfig")
        NestedConfig notDefaultConfig();


        interface NestedConfig extends Section {

            @ConfKey("someint")
            default Integer nestedInteger() {
                return 10;
            }

        }
    }

    @Test
    public void testSerialization() {
        YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(InternalConfig.class);

        Assertions.assertEquals(10, node.asMapping().value("nestedConfig").asMapping().integer("someint"));
        Assertions.assertEquals(5, node.asMapping().integer("number"));
        Assertions.assertEquals("true", node.asMapping().string("bool"));

        Assertions.assertFalse(node.isEmpty());

    }


}
