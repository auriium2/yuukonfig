package com.superyuuki.yuukonfig;

import com.amihaiemil.eoyaml.Scalar;
import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.annotate.ConfKey;
import com.superyuuki.yuukonfig.config.BaseRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class NestedSerializerTest {

    Logger logger = LoggerFactory.getLogger(NestedSerializerTest.class);

    public interface InternalConfig extends Section {

        @ConfKey("number")
        default Integer defaultInt() {
            return 5;
        }

        @ConfKey("bool")
        default Boolean defaultBool() {
            return true;
        }

        @ConfKey("nestedConfig")
        NestedConfig notDefaultConfig();


        interface NestedConfig extends Section {

            @ConfKey("someint")
            default Integer nestedInteger() {
                return 10;
            }

        }
    }

    @Test
    public void testSerialization() {
        logger.info(() -> "Starting test!");

        YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(InternalConfig.class);

        Assertions.assertFalse(node.isEmpty());

        System.out.println(node);

    }


}
