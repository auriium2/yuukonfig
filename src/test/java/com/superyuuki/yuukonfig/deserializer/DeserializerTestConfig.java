package com.superyuuki.yuukonfig.deserializer;

import com.superyuuki.yuukonfig.user.Section;
import com.superyuuki.yuukonfig.user.ConfKey;

import java.util.UUID;

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
