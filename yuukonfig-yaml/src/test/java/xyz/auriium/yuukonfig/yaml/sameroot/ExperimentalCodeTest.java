package xyz.auriium.yuukonfig.yaml.sameroot;

import xyz.auriium.yuukonfig.core.YuuKonfig;
import org.junit.jupiter.api.Test;
import xyz.auriium.yuukonfig.core.annotate.Section;

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

    }

    public interface MyConfig extends Section {

        List<String> someStrings();

    }

}
