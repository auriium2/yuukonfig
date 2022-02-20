package com.superyuuki.yuukonfig.sameroot;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.serializer.bad.InvalidSerializerTest;
import com.superyuuki.yuukonfig.user.Section;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class ExperimentalCodeTest {

    static final String testable = """
            
            someStrings:
              - "hi"
              
              
            someStrings:
              - "bye"
            
            
            
            
            """;

    @Test
    public void testLoadingSameKey() throws IOException {

        MyConfig config = YuuKonfig.instance().test().deserializeTest(testable, MyConfig.class);

        LoggerFactory.getLogger(MyConfig.class).info(() -> config.someStrings().toString());

    }

    public interface MyConfig extends Section {

        List<String> someStrings();

    }

}
