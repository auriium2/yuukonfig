package xyz.auriium.yuukonfig.toml.serializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.auriium.yuukonfig.core.YuuKonfig;
import xyz.auriium.yuukonfig.core.annotate.Section;
import xyz.auriium.yuukonfig.core.node.Node;

public class PrimitivesTest {

    @Test
    public void testSerializingPrimitives() {
        Node node = YuuKonfig.instance().test().serializeTest(PrimitivesConfig.class);

        Assertions.assertEquals(2, node.asMapping().integer("primitiveInt"));
        Assertions.assertEquals(0.0, node.asMapping().doubleNumber("primitiveDouble"));
        Assertions.assertEquals("true", node.asMapping().string("primitiveBool"));
    }

    public interface PrimitivesConfig extends Section {

        default int primitiveInt() {
            return 2;
        }

        double primitiveDouble();

        default boolean primitiveBool() {
            return true;
        }

    }

}
