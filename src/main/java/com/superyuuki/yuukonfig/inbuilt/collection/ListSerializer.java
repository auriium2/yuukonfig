package com.superyuuki.yuukonfig.inbuilt.collection;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.amihaiemil.eoyaml.YamlSequenceBuilder;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.compose.Serializer;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.impl.request.RequestImpl;
import com.superyuuki.yuukonfig.request.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Serializer for Lists. Should not be invoked when you have a custom type that has an internal list, for that
 * you should manually invoke and parse sequential nodes // this may change in the future but right now is
 * dependent on compile-time information like Type objects.
 *
 *
 */
public class ListSerializer implements Serializer {
    @Override
    public int handles(Class<?> clazz) {
        if (clazz.equals(List.class)) {
            return Priority.HANDLE;
        }

        return Priority.DONT_HANDLE;
    }

    @Override
    public YamlNode serializeDefault(Request rq, Serializers serializers) {
        return Yaml.createYamlSequenceBuilder().build(); //empty list
    }

    @Override
    public YamlNode serializeObject(Request rq, Object object, Serializers serializers) {
        String displayKey = rq.keyDisplayName().orElse("(developer error - no keypath found)");

        List<?> list = (List<?>) object; //handles should make sure nothing that is not a list is given to this object

        Optional<Type> genericType = rq.requestedType();
        if (genericType.isEmpty()) throw new MissingTypeFailure(displayKey);


        Class<?> actualTypeArgument;
        try {
             actualTypeArgument = (Class<?>) ((ParameterizedType) genericType.get()).getActualTypeArguments()[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MissingGenericFailure(1, displayKey);
        }

        YamlSequenceBuilder listBuilder = Yaml.createYamlSequenceBuilder();

        for (Object subject : list) {

            listBuilder = listBuilder.add(
                    serializers.serialize(
                            new RequestImpl(
                                    actualTypeArgument,
                                    displayKey //keep same key and lose generics
                            ),
                            subject
                    )
            );
        }


        return listBuilder.build();
    }
}
