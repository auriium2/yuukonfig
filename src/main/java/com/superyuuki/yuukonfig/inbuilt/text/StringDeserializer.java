package com.superyuuki.yuukonfig.inbuilt.text;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.serialize.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.serialize.RequestContext;
import com.superyuuki.yuukonfig.serialize.decompose.TypedDeserializer;
import com.superyuuki.yuukonfig.error.parsing.ParsingFailure;

public class StringDeserializer implements TypedDeserializer<String> {
    @Override
    public String deserialize(YamlNode node, RequestContext<String> rq, DeserializerContext ctx) throws ParsingFailure {
        return node.asScalar().value(); //ez
    }
}
