package yuukonfig.core.impl.safe;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.Node;
import xyz.auriium.yuukonstants.GenericPath;

public interface ManipulatorSafe<T>  {

    T deserialize(Node node) throws BadValueException;
    Node serializeObject(T object, GenericPath path);
    Node serializeDefault(GenericPath path);


}
