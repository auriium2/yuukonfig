package xyz.auriium.yuukonfig.yaml.serializer.bad;

import com.amihaiemil.eoyaml.YamlNode;
import xyz.auriium.yuukonfig.core.YuuKonfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.auriium.yuukonfig.core.annotate.Section;
import xyz.auriium.yuukonfig.core.node.Node;

public class InvalidSerializerTest {

    @Test
    public void testNoSerializerShouldFail() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Node node = YuuKonfig.instance().test().serializeTest(ConfigWithNonSerializableValue.class);
        });
    }

    public interface ConfigWithNonSerializableValue extends Section {

        NonSerializableValue desection();

    }

    public interface NonSerializableValue {
        void impl();
    }

    @Test
    public void testNoSerializerDefaultShouldFail() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Node node = YuuKonfig.instance().test().serializeTest(ConfigWithNonSerializableDefaultValue.class);
        });
    }

    public interface ConfigWithNonSerializableDefaultValue {

        default NonSerializableValue desection() {
            return () -> {

            };
        }

    }

    @Test
    public void testSerializableValueShouldPass() {
        Assertions.assertDoesNotThrow(() -> {
            Node node = YuuKonfig.instance().test().serializeTest(ConfigWithSerializableValue.class);
        });
    }

    public interface ConfigWithSerializableValue extends Section {

        default Integer someValue() {
            return 5;
        }

    }
}
