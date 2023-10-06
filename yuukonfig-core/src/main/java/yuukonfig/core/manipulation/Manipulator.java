package yuukonfig.core.manipulation;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.Node;
import yuukonstants.GenericPath;

/**
 * Raw manipulator that makes you do the casting yoruself. Prefer using ManipulatorSafe and HandlesSafeManipulator to wrap it
 */
public interface Manipulator {

    int handles();

    Object deserialize(Node node, GenericPath path) throws BadValueException;

    Node serializeObject(Object object, String[] comment);
    Node serializeDefault(String[] comment);

}
