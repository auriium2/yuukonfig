package xyz.auriium.yuukonfig.toml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.auriium.yuukonfig.core.YuuKonfig;
import xyz.auriium.yuukonfig.core.annotate.Section;

import java.io.IOException;

public class DeserializerTest {
    static final String CONFIG = """
              
                otherNumber = 2
                myString = "wdawdawda"
              
                [myTable]
                number = 3
                bool = false
                
                
                  
                """;

    interface Config {

        default int otherNumber() { return 2;}
        default String myString() { return "wdawdawda"; }

        Subconfig myTable();
        interface Subconfig {
            default int number() {return 3;}
            default boolean bool() {return false;}
        }


    }

    interface ConfigNoDefaults extends Section {

        int otherNumber();
        String myString();

        Subconfig myTable();
        interface Subconfig extends Section {
            int number();
            boolean bool();
        }


    }

    @Test
    public void testNestedDeserializerValuesTrue() throws IOException {
        ConfigNoDefaults config = YuuKonfig.instance().test().deserializeTest(CONFIG, ConfigNoDefaults.class);

        Assertions.assertFalse(config.myTable().bool());
        Assertions.assertEquals(3, config.myTable().number());
        Assertions.assertEquals(2, config.otherNumber());
        Assertions.assertEquals("wdawdawda", config.myString());

        Assertions.assertNotEquals(4, config.otherNumber());
    }


}
