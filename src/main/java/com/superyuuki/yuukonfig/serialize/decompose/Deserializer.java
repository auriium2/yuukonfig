package com.superyuuki.yuukonfig.serialize.decompose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.error.parsing.ParsingFailure;
import com.superyuuki.yuukonfig.serialize.RequestContext;

public interface Deserializer {

    int handles(Class<?> clazz);

    Object deserialize(YamlNode node, RequestContext<?> rq, DeserializerContext ctx) throws ParsingFailure;


}
