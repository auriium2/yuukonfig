package com.superyuuki.yuukonfig.serializer.bad;


import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.config.BaseRegistry;
import com.superyuuki.yuukonfig.error.ImpossibleReflectiveAccess;
import com.superyuuki.yuukonfig.error.TooManyArgsFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReflectiveSerializerTest {

    @Test
    public void testInlinePrivateConfigShouldFail() {

        Assertions.assertThrows(ImpossibleReflectiveAccess.class, () -> {
            YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(InlinePrivateConfig.class);
        });

    }

    interface InlinePrivateConfig extends Section {

        default Integer someValue() {
            return 5;
        }

    }

    @Test
    public void testPrivateConfigShouldFail() {

        Assertions.assertThrows(ImpossibleReflectiveAccess.class, () -> {
            YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(PackagePrivateConfig.class);
        });
    }

    public interface PublicInlineConfig extends Section {

        default Integer test() {
            return 0;
        }

    }

    @Test
    public void testPublicInlineConfigShouldPass() {

        Assertions.assertDoesNotThrow(() -> {
            YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(PublicInlineConfig.class);
        });
    }

    public interface ConfigWithParams extends Section {
        default Integer badMethod(Integer unwantedParam) {
            return 4;
        }
    }

    @Test
    public void testConfigWithParamsShouldFail() {
        Assertions.assertThrows(TooManyArgsFailure.class, () -> {
            YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(ConfigWithParams.class);
        });
    }

}
