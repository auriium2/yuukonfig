package com.superyuuki.yuukonfig.manipulation;

import java.lang.reflect.Type;

public interface ManipulatorConstructor {

    Manipulator construct(Manipulation manipulation, Class<?> useClass, Contextual<Type> useType);

}
