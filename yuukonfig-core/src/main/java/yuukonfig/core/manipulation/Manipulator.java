package yuukonfig.core.manipulation;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.Node;
import xyz.auriium.yuukonstants.GenericPath;

/**
 * Raw manipulator that makes you do the casting yoruself. Prefer using ManipulatorSafe and HandlesSafeManipulator to wrap it
 */
public interface Manipulator {

    int handles();

    Object deserialize(Node node) throws BadValueException;

    Node serializeObject(Object object, GenericPath path);
    Node serializeDefault(GenericPath path);



}
