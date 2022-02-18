package com.superyuuki.yuukonfig.serializer.bad;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.user.Section;
import com.superyuuki.yuukonfig.TestHelper;
import com.superyuuki.yuukonfig.impl.compose.NoSerializerFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvalidSerializerTest {

    @Test
    public void testNoSerializerShouldFail() {
        Assertions.assertThrows(NoSerializerFailure.class, () -> {
            YamlNode node = TestHelper.serializerTest(ConfigWithNonSerializableValue.class);
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
        Assertions.assertThrows(NoSerializerFailure.class, () -> {
            YamlNode node = TestHelper.serializerTest(ConfigWithNonSerializableDefaultValue.class);
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
            YamlNode node = TestHelper.serializerTest(ConfigWithSerializableValue.class);
        });
    }

    public interface ConfigWithSerializableValue extends Section {

        default Integer someValue() {
            return 5;
        }

    }
}
