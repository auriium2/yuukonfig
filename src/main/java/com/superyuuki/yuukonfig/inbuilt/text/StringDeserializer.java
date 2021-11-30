package com.superyuuki.yuukonfig.inbuilt.text;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.decompose.TypedDeserializer;
import com.superyuuki.yuukonfig.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.impl.decompose.ParsingFailure;
import com.superyuuki.yuukonfig.request.Request;

public class StringDeserializer implements TypedDeserializer<String> {
    @Override
    public String deserialize(YamlNode node, Request rq, DeserializerContext ctx) throws ParsingFailure {
        return node.asScalar().value(); //ez
    }
}
