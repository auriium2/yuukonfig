package yuukonfig.yaml.serializer.bad;


import yuukonfig.core.YuuKonfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yuukonfig.core.annotate.Section;
import yuukonfig.core.impl.manipulator.section.ImpossibleAccessException;
import yuukonfig.core.node.Node;

public class ReflectiveSerializerTest {

    @Test
    public void testInlinePrivateConfigShouldFail() {

        Assertions.assertThrows(ImpossibleAccessException.class, () -> {
            Node node = YuuKonfig.instance().test().serializeTest(InlinePrivateConfig.class);
        });

    }

    interface InlinePrivateConfig extends Section {

        default Integer someValue() {
            return 5;
        }

    }

    @Test
    public void testPrivateConfigShouldFail() {

        Assertions.assertThrows(ImpossibleAccessException.class, () -> {
            Node node = YuuKonfig.instance().test().serializeTest(PackagePrivateConfig.class);
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
            Node node = YuuKonfig.instance().test().serializeTest(PublicInlineConfig.class);
        });
    }

    public interface ConfigWithParams extends Section {
        default Integer badMethod(Integer unwantedParam) {
            return 4;
        }
    }

    @Test
    public void testConfigWithParamsShouldFail() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Node node = YuuKonfig.instance().test().serializeTest(ConfigWithParams.class);
        });
    }

}
