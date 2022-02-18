package com.superyuuki.yuukonfig.load;

import com.superyuuki.yuukonfig.user.Section;
import com.superyuuki.yuukonfig.impl.load.BaseLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileDefaultsTest {

    @Test
    public void testOfTheLoading(@TempDir Path file) {
        LoadableConfigTest test = BaseLoader.defaults(file, "my_config.yml", LoadableConfigTest.class).load();

        Assertions.assertEquals("woowooo", test.subsection().whatever());

        LoadableConfigTest noDefaults = BaseLoader.defaults(file, "my_config.yml", LoadableConfigTest.class).loadWithoutDefaults();

        Assertions.assertEquals("woowooo", noDefaults.subsection().whatever());


    }

    public interface LoadableConfigTest extends Section {

        default Integer someInteger() {
            return 5;
        }

        default String someString() {
            return "HELLO";
        }

        default List<String> whatever() {
            List<String> strings = new ArrayList<>();

            strings.add("awawa");
            strings.add("lalalaa");

            return strings;
        }

        Sub subsection();

        interface Sub extends Section {
            default String whatever() {
                return "woowooo";
            }

            default UUID i_dont_care() {
                return UUID.randomUUID();
            }
        }


    }

}
