package com.superyuuki.yuukonfig.decompose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.impl.decompose.DeserializerContextImpl;
import com.superyuuki.yuukonfig.request.Request;
import com.superyuuki.yuukonfig.request.UserRequest;

public interface Deserializers {

    <T> T deserializeTyped(YamlNode node, UserRequest<T> rq, DeserializerContext ctx);
    Object deserialize(YamlNode node, Request rq, DeserializerContext ctx);

    default <T> T deserializeTyped(YamlNode node, UserRequest<T> rq, String configName) {
        return deserializeTyped(node, rq, new DeserializerContextImpl(this, configName));
    }


}
