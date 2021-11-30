package com.superyuuki.yuukonfig.compose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.request.Request;

public interface Serializer {

    int handles(Class<?> clazz);

    YamlNode serializeDefault(Request rq, Serializers serializers);
    YamlNode serializeObject(Request rq, Object object, Serializers serializers);

}
