package com.superyuuki.yuukonfig.inbuilt.text;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.decompose.TypedDeserializer;
import com.superyuuki.yuukonfig.impl.decompose.ParsingFailure;
import com.superyuuki.yuukonfig.request.Request;

import java.util.UUID;

public class UUIDDeserializer implements TypedDeserializer<UUID> {

    @Override
    public UUID deserialize(YamlNode node, Request rq, DeserializerContext ctx) throws ParsingFailure {
        try {
            return UUID.fromString(node.asScalar().value());
        } catch (IllegalArgumentException exception) {
            throw new ParsingFailure(rq, ctx, "Value is not a UUID!");
        }
    }
}
