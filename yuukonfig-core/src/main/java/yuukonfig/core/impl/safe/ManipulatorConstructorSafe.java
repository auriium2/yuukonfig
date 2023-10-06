package yuukonfig.core.impl.safe;

import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulation;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.node.RawNodeFactory;

import java.lang.reflect.Type;

public interface ManipulatorConstructorSafe<T> {

    ManipulatorSafe<T> construct(Manipulation manipulation, Class<T> useClass, Contextual<Type> useType, RawNodeFactory factory);

}
