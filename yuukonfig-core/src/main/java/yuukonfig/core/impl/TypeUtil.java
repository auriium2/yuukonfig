package yuukonfig.core.impl;

import yuukonfig.core.manipulation.Contextual;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeUtil {

    public static Class<?> parameterizedClassFromType(Contextual<Type> type, BaseManipulation manipulation) {
        Class<?> actualTypeArgument;
        try {
            actualTypeArgument = (Class<?>) ((ParameterizedType) type.get()).getActualTypeArguments()[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalStateException("not enough arguments for generic in"  + manipulation.configName());
        }

        return actualTypeArgument;
    }

}
