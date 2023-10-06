package yuukonfig.core.manipulation;

import yuukonfig.core.node.RawNodeFactory;

import java.lang.reflect.Type;

public interface ManipulatorConstructor {

    Manipulator construct(Manipulation manipulation, Class<?> useClass, Contextual<Type> useType, RawNodeFactory factory);

}
