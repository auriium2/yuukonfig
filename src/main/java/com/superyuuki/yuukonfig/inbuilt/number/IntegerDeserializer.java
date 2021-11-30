package com.superyuuki.yuukonfig.inbuilt.number;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.decompose.TypedDeserializer;
import com.superyuuki.yuukonfig.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.impl.decompose.ParsingFailure;
import com.superyuuki.yuukonfig.request.Request;

public class IntegerDeserializer implements TypedDeserializer<Integer> {

    @Override
    public Integer deserialize(YamlNode node, Request rq, DeserializerContext ctx) {
        try {
            return Integer.parseInt(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new ParsingFailure(rq, ctx, "Value is not an integer!");
        }

    }
}
