package com.superyuuki.yuukonfig.inbuilt;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.user.Section;
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
        YamlNode node = YuuKonfig.instance().test().serializeTest(EnumConfig.class);

        Assertions.assertEquals("COWS", node.asMapping().string("dongle"));
    }

    static final String SOME_ENUM_CONFIG = """
            
            dongle: house
            
            """;

    @Test
    public void testDeserializingEnums() throws IOException {
        EnumConfig config = YuuKonfig.instance().test().deserializeTest(SOME_ENUM_CONFIG, EnumConfig.class);

        Assertions.assertEquals(Dingle.HOUSE, config.dongle());
    }

}
