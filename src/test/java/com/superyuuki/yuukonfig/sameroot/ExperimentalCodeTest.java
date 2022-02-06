package com.superyuuki.yuukonfig.sameroot;

import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.List;


public class ExperimentalCodeTest {

    static final String testable = """
            
            someStrings:
              - "hi"
              
              
            someStrings:
              - "bye"
            
            
            
            
            """;

    @Test
    public void testLoadingSameKey() {

        MyConfig config = TestHelper.deserializerTest(testable, MyConfig.class);

        LoggerFactory.getLogger(MyConfig.class).info(() -> config.someStrings().toString());

    }

    public interface MyConfig extends Section {

        List<String> someStrings();

    }

}
