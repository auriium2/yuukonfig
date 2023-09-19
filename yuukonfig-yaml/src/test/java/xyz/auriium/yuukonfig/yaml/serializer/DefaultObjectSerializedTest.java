package xyz.auriium.yuukonfig.yaml.serializer;

import com.amihaiemil.eoyaml.YamlNode;
import xyz.auriium.yuukonfig.core.YuuKonfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.auriium.yuukonfig.core.annotate.Key;
import xyz.auriium.yuukonfig.core.annotate.Section;
import xyz.auriium.yuukonfig.core.node.Node;

public class DefaultObjectSerializedTest {

    public interface DefaultObjectConfig extends Section {

        @Key("get_trolled_a248")
        default String someValue() {
            return "yuukonfig is better";
        }

    }

    @Test
    public void testDefaultObjectCanBeSerialized() {

        Node node = YuuKonfig.instance().test().serializeTest(DefaultObjectConfig.class);

        Assertions.assertEquals("yuukonfig is better", node.asMapping().string("get_trolled_a248"));
    }

}
