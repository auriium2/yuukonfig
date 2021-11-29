package com.superyuuki.yuukonfig.serialize.compose.reflection;

import com.amihaiemil.eoyaml.*;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.annotate.ConfComments;
import com.superyuuki.yuukonfig.annotate.ConfKey;
import com.superyuuki.yuukonfig.serialize.compose.Serializer;
import com.superyuuki.yuukonfig.serialize.compose.Serializers;
import com.superyuuki.yuukonfig.error.TooManyArgsFailure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
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

        T defaultProxy = request.cast(Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{ request },
                (a,b,c) -> {
                    if (b.isDefault()) {
                        return InvocationHandler.invokeDefault(a, b, c);
                    }

                    throw new IllegalStateException("Defaulting anonymous proxy does not support normal method queries!");
                }
        ));

        Map<String, YamlNode> yamlNodeMap = new HashMap<>();

        for (Method method : request.getMethods()) {
            if (method.getParameterCount() != 0) throw new TooManyArgsFailure(method.getName());

            if (method.isDefault()) {
                //get me a value!

                Object child = new ProxyForwarder(method, defaultProxy).invoke();

                yamlNodeMap.put(getKey(method), serializers.serialize(child));

            } else {

                YamlNode subnode = serializers.serializeDefault(method.getReturnType());
                //get me whatever interface there is, and tell it to bring us a value (later)!


                yamlNodeMap.put(getKey(method), subnode);
            }
        }

        YamlMappingBuilder mappingBuilder = Yaml.createYamlMappingBuilder();

        for (Map.Entry<String, YamlNode> nodeEntry : yamlNodeMap.entrySet()) {
            mappingBuilder = mappingBuilder.add(nodeEntry.getKey(), nodeEntry.getValue());

        }

        return mappingBuilder.build();


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

            builder = builder.add(
                    getKey(method),
                    serializers.serialize(new ProxyForwarder(method, object).invoke())
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
