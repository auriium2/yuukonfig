package xyz.auriium.yuukonfig.toml.sameroot;

import org.junit.jupiter.api.Test;
import xyz.auriium.yuukonfig.core.YuuKonfig;
import xyz.auriium.yuukonfig.core.annotate.Section;

import java.io.IOException;
import java.util.List;


public class ExperimentalCodeTest {

    static final String testable = """
            
            someStrings = ["hi"]
            someStrings = ["bye"]
            
            
            
            
            """;

    @Test
    public void testLoadingSameKey() throws IllegalStateException, IOException {

        //TODO idk why this happens
        //MyConfig config = YuuKonfig.instance().test().deserializeTest(testable, MyConfig.class);

    }

    public interface MyConfig extends Section {

        List<String> someStrings();

    }

}
