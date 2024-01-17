package yuukonfig.core;

import xyz.auriium.yuukonstants.GenericPath;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.node.Node;

import java.lang.reflect.Type;

public interface SerializeFunction {

    Node serialize(Object o, Class<?> c, GenericPath p, Contextual<Type> ctx);

}
