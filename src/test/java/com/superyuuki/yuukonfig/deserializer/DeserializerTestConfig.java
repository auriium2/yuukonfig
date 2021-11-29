package com.superyuuki.yuukonfig.deserializer;

import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.annotate.ConfKey;

public interface DeserializerTestConfig extends Section {

    @ConfKey("number")
    Integer defaultInt();

    @ConfKey("bool")
    Boolean defaultBool();

    @ConfKey("nestedConfig")
    NestedConfig notDefaultConfig();


    interface NestedConfig extends Section {

        @ConfKey("someint")
        Integer nestedInteger();

    }

}
