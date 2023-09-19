package xyz.auriium.yuukonfig.toml.deserializer;

import xyz.auriium.yuukonfig.core.annotate.Key;
import xyz.auriium.yuukonfig.core.annotate.Section;

public interface DeserializerTestConfig extends Section {

    @Key("number")
    Integer defaultInt();

    @Key("bool")
    Boolean defaultBool();

    @Key("nestedConfig")
    NestedConfig notDefaultConfig();

    interface NestedConfig extends Section {

        @Key("someint")
        Integer nestedInteger();

    }

}
