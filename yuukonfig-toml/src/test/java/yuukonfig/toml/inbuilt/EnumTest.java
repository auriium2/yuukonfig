package yuukonfig.toml.inbuilt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yuukonfig.core.YuuKonfig;
import yuukonfig.core.annotate.Section;
import yuukonfig.core.node.Node;

import java.io.IOException;

public class EnumTest {

    public enum Dingle {
        HOUSE,
        COWS
    }

    public interface EnumConfig extends Section {

        default Dingle dongle() {
            return Dingle.COWS;
        }

    }

    @Test
    public void testSerializingEnums() {
        Node node = YuuKonfig.instance().test().serializeTest(EnumConfig.class);

        Assertions.assertEquals("COWS", node.asMapping().string("dongle"));
    }

    static final String SOME_ENUM_CONFIG = """
            
            dongle = "house"
            
            """;

    @Test
    public void testDeserializingEnums() throws IOException {
        EnumConfig config = YuuKonfig.instance().test().deserializeTest(SOME_ENUM_CONFIG, EnumConfig.class);

        Assertions.assertEquals(Dingle.HOUSE, config.dongle());
    }

}
