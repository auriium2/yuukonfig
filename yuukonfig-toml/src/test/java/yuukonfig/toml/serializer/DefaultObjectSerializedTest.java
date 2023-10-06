package yuukonfig.toml.serializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yuukonfig.core.YuuKonfig;
import yuukonfig.core.annotate.Key;
import yuukonfig.core.annotate.Section;
import yuukonfig.core.node.Node;

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
