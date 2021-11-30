package com.superyuuki.yuukonfig.decompose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.impl.decompose.ParsingFailure;
import com.superyuuki.yuukonfig.request.Request;

public interface Deserializer {

    int handles(Class<?> clazz);

    Object deserialize(YamlNode node, Request rq, DeserializerContext ctx) throws ParsingFailure;


}
