package com.superyuuki.yuukonfig.compose;

import com.amihaiemil.eoyaml.YamlNode;

public interface Serializers {

    YamlNode serialize(Object object);
    YamlNode serializeDefault(Class<?> clazz);

}
