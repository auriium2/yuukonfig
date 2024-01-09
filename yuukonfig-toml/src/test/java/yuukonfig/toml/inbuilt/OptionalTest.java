package yuukonfig.toml.inbuilt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yuukonfig.core.YuuKonfig;
import yuukonfig.core.annotate.Section;
import yuukonfig.toml.inbuilt.ArrayTest;
import yuukonstants.exception.ExceptionUtil;

import java.io.IOException;
import java.util.Optional;

public class OptionalTest {

    public interface OptionalConfig extends Section {
        Optional<Integer> optionalInteger();
    }

    public static String TOML_WITHOUT = """
            
            
            """;

    public static String TOML_WITH = """
            
            optionalInteger = 2
            
            """;


    @Test
    public void testDeserializingArray() {

        ExceptionUtil.wrapExceptionalRunnable(() -> {
            try {
                OptionalConfig config = YuuKonfig.instance().test().deserializeTest(TOML_WITHOUT, OptionalConfig.class);

                Assertions.assertTrue(config.optionalInteger().isEmpty());

                OptionalConfig config2 = YuuKonfig.instance().test().deserializeTest(TOML_WITH, OptionalConfig.class);

                Assertions.assertTrue(config2.optionalInteger().isPresent());


            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

        }).run();



    }

}
