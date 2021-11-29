package com.superyuuki.yuukonfig.serializer.bad;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.BaseRegistry;
import com.superyuuki.yuukonfig.error.NoSerializerFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvalidSerializerTest {

    @Test
    public void testNoSerializerShouldFail() {
        Assertions.assertThrows(NoSerializerFailure.class, () -> {
            YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(ConfigWithNonSerializableValue.class);
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
            YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(ConfigWithNonSerializableDefaultValue.class);
        });
    }

    public interface ConfigWithNonSerializableDefaultValue {

        default NonSerializableValue desection() {
            return new NonSerializableValue() {
                @Override
                public void impl() {

                }
            };
        }

    }

    @Test
    public void testSerializableValueShouldPass() {
        Assertions.assertDoesNotThrow(() -> {
            YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(ConfigWithSerializableValue.class);
        });
    }

    public interface ConfigWithSerializableValue extends Section {

        default Integer someValue() {
            return 5;
        }

    }
}
