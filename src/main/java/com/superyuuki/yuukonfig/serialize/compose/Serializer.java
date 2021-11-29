package com.superyuuki.yuukonfig.serialize.compose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.serialize.RequestContext;

public interface Serializer {

    int handles(Class<?> clazz);

    YamlNode serializeDefault(RequestContext<?> rq, Serializers serializers);
    YamlNode serializeObject(RequestContext<?> rq, Object object, Serializers serializers);

}
