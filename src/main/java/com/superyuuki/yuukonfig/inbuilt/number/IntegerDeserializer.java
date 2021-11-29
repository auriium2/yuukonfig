package com.superyuuki.yuukonfig.inbuilt.number;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.serialize.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.serialize.RequestContext;
import com.superyuuki.yuukonfig.serialize.decompose.TypedDeserializer;
import com.superyuuki.yuukonfig.error.parsing.ParsingFailure;

public class IntegerDeserializer implements TypedDeserializer<Integer> {

    @Override
    public Integer deserialize(YamlNode node, RequestContext<Integer> rq, DeserializerContext ctx) {
        try {
            return Integer.parseInt(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new ParsingFailure(rq, ctx, "Value is not an integer!");
        }

    }
}
