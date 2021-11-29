package com.superyuuki.yuukonfig.compose;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMappingBuilder;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.annotate.ConfComments;
import com.superyuuki.yuukonfig.annotate.ConfKey;
import com.superyuuki.yuukonfig.error.TooManyArgsFailure;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;

public class InterfaceSerializer implements Serializer {
    @Override
    public int handles(Class<?> clazz) {
        if (Section.class.isAssignableFrom(clazz)) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public YamlNode serializeDefault(Class<?> request, Serializers serializers) {
        return maintainType(request, serializers);
    }

    //Called when an interface-only subsection is found, such as <? extends Subsection> configKey() in an interface
    @SuppressWarnings("unchecked")
    <T> YamlNode maintainType(Class<T> request, Serializers serializers) {

        YamlMappingBuilder builder = Yaml.createYamlMappingBuilder();

        T defaultInvoke = (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { request },
                new DefaultInvocationHandler<>( request )
        );

        for (Method method : request.getMethods()) {
            if (method.getParameterCount() != 0) throw new TooManyArgsFailure(method.getName());

            if (method.isDefault()) {
                //get me a value!
                Object child = new InvocationForwarder(method, defaultInvoke).invoke();

                builder.add(
                        getKey(method),
                        serializers.serialize(child)
                );

            } else {
                //get me whatever interface there is, and tell it to bring us a value (later)!

                builder.add(
                        getKey(method),
                        serializers.serialize(method.getReturnType())
                );
            }
        }

        return builder.build();
    }

    //called when an implementation of a section is found, via default <? extends Section> configKey() {} in an interface
    @Override
    public YamlNode serializeObject(Class<?> request, Object object, Serializers serializers) {
        return maintainTypeObject(request, object, serializers);
    }

    <T> YamlNode maintainTypeObject(Class<T> request, Object object, Serializers serializers) {
        //it's an object (implementation of interface), so it can't have any default methods. Therefore, all we need to worry about is getting
        //all of its' children.

        YamlMappingBuilder builder = Yaml.createYamlMappingBuilder();

        for (Method method : request.getMethods()) {
            if (method.getParameterCount() != 0) throw new TooManyArgsFailure(method.getName());

            builder.add(
                    getKey(method),
                    serializers.serialize(new InvocationForwarder(method, object).invoke())
            );
        }

        return builder.build();
    }

    String getKey(Method method) {
        if (method.isAnnotationPresent(ConfKey.class)) {
            return method.getAnnotation(ConfKey.class).value();
        }

        return method.getName();
    }

    Optional<String[]> getComments(Method method) {
        if (method.isAnnotationPresent(ConfComments.class)) {
            return Optional.of(method.getAnnotation(ConfComments.class).value());
        }
        return Optional.empty();
    }
}
