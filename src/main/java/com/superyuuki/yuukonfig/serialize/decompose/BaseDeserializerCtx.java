package com.superyuuki.yuukonfig.serialize.decompose;

public record BaseDeserializerCtx(Deserializers context, String configDisplayName) implements DeserializerContext {

    @Override
    public Deserializers deserializers() {
        return context;
    }
}
