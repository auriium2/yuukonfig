package com.superyuuki.yuukonfig.compose;

import com.amihaiemil.eoyaml.YamlNode;

public interface Serializer {

    int handles(Class<?> clazz);

    YamlNode serializeDefault(Class<?> request, Serializers serializers);
    YamlNode serializeObject(Class<?> request, Object object, Serializers serializers);

}
