package yuukonfig.core.impl.safe;

import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.node.RawNodeFactory;

import java.lang.reflect.Type;

public interface ManipulatorConstructorSafe<T> {

    ManipulatorSafe<T> construct(BaseManipulation manipulation, Class<T> useClass, Contextual<Type> useType, RawNodeFactory factory);

}
