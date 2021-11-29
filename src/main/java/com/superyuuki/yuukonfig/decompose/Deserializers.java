package com.superyuuki.yuukonfig.decompose;

import com.amihaiemil.eoyaml.YamlNode;

public interface Deserializers {

    <T> T deserialize(YamlNode node, Class<? extends T> clazz);

}
