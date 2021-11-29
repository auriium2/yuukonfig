package com.superyuuki.yuukonfig.serialize.decompose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.serialize.RequestContext;

public interface Deserializers {

    <T> T deserialize(YamlNode node, RequestContext<T> rq, DeserializerContext ctx);
    <T> T deserialize(YamlNode node, Class<T> rq, String configName);

}
