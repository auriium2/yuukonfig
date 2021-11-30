package com.superyuuki.yuukonfig.impl.decompose;

import com.superyuuki.yuukonfig.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.decompose.Deserializers;

public record DeserializerContextImpl(Deserializers context, String configDisplayName) implements DeserializerContext {

    @Override
    public Deserializers deserializers() {
        return context;
    }
}
