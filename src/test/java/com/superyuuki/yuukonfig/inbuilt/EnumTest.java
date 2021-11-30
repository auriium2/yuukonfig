package com.superyuuki.yuukonfig.inbuilt;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.TestHelper;
import com.superyuuki.yuukonfig.impl.load.BaseRegistry;
import com.superyuuki.yuukonfig.impl.request.UserRequestImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        YamlNode node = TestHelper.serializerTest(EnumConfig.class);

        Assertions.assertEquals("COWS", node.asMapping().string("dongle"));
    }

    static final String SOME_ENUM_CONFIG = """
            
            dongle: house
            
            """;

    @Test
    public void testDeserializingEnums() throws IOException {
        YamlNode node = Yaml.createYamlInput(SOME_ENUM_CONFIG).readYamlMapping();

        EnumConfig config = BaseRegistry.defaults().makeDeserializers().deserializeTyped(
                node,
                new UserRequestImpl<>(EnumConfig.class),
                "virtualconfig"
        );

        Assertions.assertEquals(Dingle.HOUSE, config.dongle());
    }

}
