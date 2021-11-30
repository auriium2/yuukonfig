package com.superyuuki.yuukonfig.inbuilt.number;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.decompose.TypedDeserializer;
import com.superyuuki.yuukonfig.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.impl.decompose.ParsingFailure;
import com.superyuuki.yuukonfig.request.Request;

public class DoubleDeserializer implements TypedDeserializer<Double> {

    @Override
    public Double deserialize(YamlNode node, Request rq, DeserializerContext ctx) throws ParsingFailure {
        try {
            return Double.parseDouble(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new ParsingFailure(rq, ctx, "Value is not a double!");
        }
    }
}
