package yuukonfig.core.manipulation;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.Node;
import yuukonstants.GenericPath;

import java.lang.reflect.Type;

public interface Manipulation {

    String configName();
    String fullConfigName();

    default Node serialize(Object object, Class<?> under, String[] comment) {
        return serialize(object, under, comment, Contextual.empty());
    }

    default Node serializeDefault(Class<?> type, String[] comment) {
        return serializeDefault(type, comment, Contextual.empty());
    }

    Node serialize(Object object, Class<?> under, String[] comment, Contextual<Type> typeCtx);
    Node serializeDefault(Class<?> type, String[] comment, Contextual<Type> typeCtx);

    default Object deserialize(Node node, GenericPath nextPath, Class<?> as) {
        return deserialize(node, nextPath, as, Contextual.empty());
    }

    Object deserialize(Node node, GenericPath nextPath, Class<?> as, Contextual<Type> typeCtx) throws BadValueException;

}
