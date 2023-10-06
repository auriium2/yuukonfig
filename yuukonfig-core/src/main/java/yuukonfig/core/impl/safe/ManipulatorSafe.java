package yuukonfig.core.impl.safe;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.Node;
import yuukonstants.GenericPath;

public interface ManipulatorSafe<T>  {

    T deserialize(Node node, GenericPath path) throws BadValueException;
    Node serializeObject(T object, String[] comment);
    Node serializeDefault(String[] comment);


}
