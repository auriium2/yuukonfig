package com.superyuuki.yuukonfig.manipulation;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.BadValueException;

import java.lang.reflect.Type;

public interface Manipulation {

    String configName();

    default YamlNode serialize(Object object, String[] comment) {
        return serialize(object, comment, Contextual.empty());
    }
    default YamlNode serializeDefault(Class<?> type, String[] comment) {
        return serializeDefault(type, comment, Contextual.empty());
    }

    YamlNode serialize(Object object, String[] comment, Contextual<Type> typeCtx);
    YamlNode serializeDefault(Class<?> type, String[] comment, Contextual<Type> typeCtx);

    default Object deserialize(YamlNode node, String key, Class<?> as) {
        return deserialize(node, key, as, Contextual.empty());
    }

    Object deserialize(YamlNode node, String key, Class<?> as, Contextual<Type> typeCtx) throws BadValueException;

}
