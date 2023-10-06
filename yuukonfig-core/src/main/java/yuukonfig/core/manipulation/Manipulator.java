package yuukonfig.core.manipulation;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.Node;
import yuukonstants.GenericPath;

public interface Manipulator {

    int handles();

    Object deserialize(Node node, GenericPath path) throws BadValueException;

    Node serializeObject(Object object, String[] comment);
    Node serializeDefault(String[] comment);

}
