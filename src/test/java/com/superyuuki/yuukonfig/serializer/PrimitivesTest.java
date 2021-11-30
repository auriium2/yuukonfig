package com.superyuuki.yuukonfig.serializer;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimitivesTest {

    @Test
    public void testSerializingPrimitives() {
        YamlNode node = TestHelper.serializerTest(PrimitivesConfig.class);

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
