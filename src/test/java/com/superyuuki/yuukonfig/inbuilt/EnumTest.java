package com.superyuuki.yuukonfig.inbuilt;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.BaseRegistry;
import com.superyuuki.yuukonfig.Section;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

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
        YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(EnumConfig.class);

        Assertions.assertEquals("COWS", node.asMapping().string("dongle"));
    }

    static final String SOME_ENUM_CONFIG = """
            
            dongle: house
            
            """;

    @Test
    public void testDeserializingEnums() throws IOException {
        YamlNode node = Yaml.createYamlInput(SOME_ENUM_CONFIG).readYamlMapping();

        EnumConfig config = BaseRegistry.defaults().makeDeserializers().deserialize(node, EnumConfig.class, "virtualconfig");

        Assertions.assertEquals(Dingle.HOUSE, config.dongle());
    }

}
