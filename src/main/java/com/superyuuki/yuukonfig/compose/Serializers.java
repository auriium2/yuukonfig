package com.superyuuki.yuukonfig.compose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.impl.request.RequestImpl;
import com.superyuuki.yuukonfig.request.Request;

public interface Serializers {

    YamlNode serialize(Request rq, Object object);
    YamlNode serializeDefault(Request request); //for default, allow class

    /**
     * Serialize from a class using a parser designed for the first node in the tree (provide no extra context about what is being parsed such as
     * the display name of the key or any generic type).
     * 
     * @deprecated use {@link #serializeDefault(Request)} so that serializers properly receive context they can use to relay error messages
     * @param clazz the class to serialize
     * @return a yaml node
     */
    @Deprecated 
    default YamlNode serializeInitial(Class<?> clazz) {
        return serializeDefault(new RequestImpl(clazz));
    }

}
