package xyz.auriium.yuukonfig.toml.inbuilt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.auriium.yuukonfig.core.YuuKonfig;
import xyz.auriium.yuukonfig.core.annotate.Section;
import xyz.auriium.yuukonfig.core.node.Node;

import java.io.IOException;
import java.util.UUID;

public class UUIDTest {

    static final String SOME_UUID_CONFIG = """
            
            someUUID = "8046dfc5-4ef7-4bd5-831b-05978815148f"
            
            """;

    @Test
    public void testSerializingUUID() {
        Node node = YuuKonfig.instance().test().serializeTest(UUIDConfig.class);

        Assertions.assertEquals("8046dfc5-4ef7-4bd5-831b-05978815148f", node.asMapping().string("someUUID"));
    }

    public interface UUIDConfig extends Section {
        default UUID someUUID() {
            return UUID.fromString("8046dfc5-4ef7-4bd5-831b-05978815148f");
        }
    }

    @Test
    public void testDeserializingUUID() throws IOException {
        UUIDConfig config = YuuKonfig.instance().test().deserializeTest(SOME_UUID_CONFIG, UUIDConfig.class);

        Assertions.assertEquals(UUID.fromString("8046dfc5-4ef7-4bd5-831b-05978815148f"), config.someUUID());
    }

}
