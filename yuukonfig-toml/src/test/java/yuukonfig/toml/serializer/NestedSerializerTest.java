package yuukonfig.toml.serializer;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yuukonfig.core.YuuKonfig;
import yuukonfig.core.annotate.Key;
import yuukonfig.core.annotate.Section;
import yuukonfig.core.node.Node;

public class NestedSerializerTest {

    public interface InternalConfig extends Section {

        @Key("number")
        default Integer defaultInt() {
            return 5;
        }

        @Key("bool")
        default Boolean defaultBool() {
            return true;
        }

        @Key("nestedConfig")
        NestedConfig notDefaultConfig();


        interface NestedConfig extends Section {

            @Key("someint")
            default Integer nestedInteger() {
                return 10;
            }

        }
    }

    @Test
    public void testSerialization() {
        Node node = YuuKonfig.instance().test().serializeTest(InternalConfig.class);

        Assertions.assertEquals(10, node.asMapping().valuePossiblyMissing("nestedConfig").asMapping().integer("someint"));
        Assertions.assertEquals(5, node.asMapping().integer("number"));
        Assertions.assertEquals("true", node.asMapping().string("bool"));

        Assertions.assertFalse(node.isEmpty());

    }


}
