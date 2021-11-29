package com.superyuuki.yuukonfig.decompose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.error.parsing.ParsingFailure;

public interface Deserializer {

    int handles(Class<?> clazz);

    Object deserialize(YamlNode node, RequestContext<?> rq, DeserializerContext ctx) throws ParsingFailure;


}
