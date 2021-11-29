package com.superyuuki.yuukonfig.decompose;

import com.amihaiemil.eoyaml.YamlNode;

public interface Deserializers {

    <T> T deserialize(YamlNode node, RequestContext<T> rq, DeserializerContext ctx);
    <T> T deserialize(YamlNode node, Class<T> rq, String configName);

}
