package com.superyuuki.yuukonfig.inbuilt.bool;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.decompose.RequestContext;
import com.superyuuki.yuukonfig.decompose.TypedDeserializer;
import com.superyuuki.yuukonfig.error.parsing.ParsingFailure;

public class BooleanDeserializer implements TypedDeserializer<Boolean> {

    @Override
    public Boolean deserialize(YamlNode node, RequestContext<Boolean> rq, DeserializerContext ctx) throws ParsingFailure {
        try {
            return Boolean.parseBoolean(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new ParsingFailure(rq, ctx, "Value is not a boolean!");
        }
    }
}
