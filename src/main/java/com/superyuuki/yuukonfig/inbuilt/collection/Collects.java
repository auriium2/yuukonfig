package com.superyuuki.yuukonfig.inbuilt.collection;

import com.superyuuki.yuukonfig.request.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public class Collects {

    static Class<?> getGenericType(Request request, String displayKey) {
        Optional<Type> genericType = request.requestedType();
        if (genericType.isEmpty()) throw new MissingTypeFailure(displayKey);


        Class<?> actualTypeArgument;
        try {
            actualTypeArgument = (Class<?>) ((ParameterizedType) genericType.get()).getActualTypeArguments()[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MissingGenericFailure(1, displayKey);
        }

        return actualTypeArgument;
    }

}
