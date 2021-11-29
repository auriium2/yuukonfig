package com.superyuuki.yuukonfig.decompose;

import com.amihaiemil.eoyaml.YamlNode;

public interface Deserializer {

    int handles(Class<?> clazz);

    Object deserialize(Class<?> requestedClass, YamlNode node, Deserializers deserializers);


}
