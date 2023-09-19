package xyz.auriium.yuukonfig.core.manipulation;

import xyz.auriium.yuukonfig.core.err.BadValueException;
import xyz.auriium.yuukonfig.core.node.Node;

import java.lang.reflect.Type;

public interface Manipulation {

    String configName();

    default Node serialize(Object object, Class<?> under, String[] comment) {
        return serialize(object, under, comment, Contextual.empty());
    }
    default Node serializeDefault(Class<?> type, String[] comment) {
        return serializeDefault(type, comment, Contextual.empty());
    }

    Node serialize(Object object, Class<?> under, String[] comment, Contextual<Type> typeCtx);
    Node serializeDefault(Class<?> type, String[] comment, Contextual<Type> typeCtx);

    default Object deserialize(Node node, String key, Class<?> as) {
        return deserialize(node, key, as, Contextual.empty());
    }

    Object deserialize(Node node, String key, Class<?> as, Contextual<Type> typeCtx) throws BadValueException;

}
