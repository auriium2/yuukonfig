package yuukonfig.toml.deserializer;

import yuukonfig.core.annotate.Key;
import yuukonfig.core.annotate.Section;

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
