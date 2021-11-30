package com.superyuuki.yuukonfig.inbuilt.bool;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.decompose.TypedDeserializer;
import com.superyuuki.yuukonfig.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.impl.decompose.ParsingFailure;
import com.superyuuki.yuukonfig.request.Request;

public class BooleanDeserializer implements TypedDeserializer<Boolean> {

    @Override
    public Boolean deserialize(YamlNode node, Request rq, DeserializerContext ctx) throws ParsingFailure {
        try {
            return Boolean.parseBoolean(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new ParsingFailure(rq, ctx, "Value is not a boolean!");
        }
    }
}
