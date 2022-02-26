package com.superyuuki.yuukonfig.serializer.bad;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.user.Section;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvalidSerializerTest {

    @Test
    public void testNoSerializerShouldFail() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            YamlNode node = YuuKonfig.instance().test().serializeTest(ConfigWithNonSerializableValue.class);
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
            YamlNode node = YuuKonfig.instance().test().serializeTest(ConfigWithNonSerializableDefaultValue.class);
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
            YamlNode node = YuuKonfig.instance().test().serializeTest(ConfigWithSerializableValue.class);
        });
    }

    public interface ConfigWithSerializableValue extends Section {

        default Integer someValue() {
            return 5;
        }

    }
}
