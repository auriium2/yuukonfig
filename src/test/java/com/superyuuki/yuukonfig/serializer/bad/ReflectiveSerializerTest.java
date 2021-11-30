package com.superyuuki.yuukonfig.serializer.bad;


import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.TestHelper;
import com.superyuuki.yuukonfig.impl.load.BaseRegistry;
import com.superyuuki.yuukonfig.inbuilt.section.ImpossibleReflectiveAccess;
import com.superyuuki.yuukonfig.inbuilt.section.TooManyArgsFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReflectiveSerializerTest {

    @Test
    public void testInlinePrivateConfigShouldFail() {

        Assertions.assertThrows(ImpossibleReflectiveAccess.class, () -> {
            YamlNode node = TestHelper.serializerTest(InlinePrivateConfig.class);
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
            YamlNode node = TestHelper.serializerTest(PackagePrivateConfig.class);
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
            YamlNode node = TestHelper.serializerTest(PublicInlineConfig.class);
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
            YamlNode node = TestHelper.serializerTest(ConfigWithParams.class);
        });
    }

}
