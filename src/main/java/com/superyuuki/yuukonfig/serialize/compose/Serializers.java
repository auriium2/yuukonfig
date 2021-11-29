package com.superyuuki.yuukonfig.serialize.compose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.serialize.RequestContext;

public interface Serializers {

    YamlNode serialize(Object object); //TODO WHAT
    YamlNode serializeDefault(RequestContext<?> requestContext); //for default, allow class

}
