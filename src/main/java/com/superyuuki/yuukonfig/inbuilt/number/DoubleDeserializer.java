package com.superyuuki.yuukonfig.inbuilt.number;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.serialize.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.serialize.RequestContext;
import com.superyuuki.yuukonfig.serialize.decompose.TypedDeserializer;
import com.superyuuki.yuukonfig.error.parsing.ParsingFailure;

public class DoubleDeserializer implements TypedDeserializer<Double> {

    @Override
    public Double deserialize(YamlNode node, RequestContext<Double> rq, DeserializerContext ctx) throws ParsingFailure {
        try {
            return Double.parseDouble(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new ParsingFailure(rq, ctx, "Value is not a double!");
        }
    }
}
