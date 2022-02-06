package com.superyuuki.yuukonfig.inbuilt.section;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMappingBuilder;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.annotate.ConfKey;
import com.superyuuki.yuukonfig.compose.Serializer;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.impl.request.RequestImpl;
import com.superyuuki.yuukonfig.request.Request;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class SectionSerializer implements Serializer {

    @Override
    public int handles(Class<?> clazz) {
        if (Section.class.isAssignableFrom(clazz)) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public YamlNode serializeDefault(Request request, Serializers serializers) {
        return maintainType(request, serializers);
    }

    //Called when an interface-only subsection is found, such as <? extends Subsection> configKey() in an interface
    @SuppressWarnings("unchecked")
    <T> YamlNode maintainType(Request ctx, Serializers serializers) {

        Class<? extends T> request = (Class<? extends T>) ctx.requestedClass();

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

            String key = getKey(method);

            if (method.isDefault()) {
                //get me a value!

                Object child = new ProxyForwarder(method, defaultProxy).invoke();

                yamlNodeMap.put(
                        key,
                        serializers.serialize(
                                new RequestImpl(
                                        method.getReturnType(),
                                        method.getGenericReturnType(),
                                        key
                                ),
                                child
                        )
                );

            } else {

                YamlNode subnode = serializers.serializeDefault(
                        new RequestImpl(
                                method.getReturnType(),
                                method.getGenericReturnType(),
                                key
                        )
                );
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
    public YamlNode serializeObject(Request request, Object object, Serializers serializers) {
        Class<?> clazz = request.requestedClass();
        YamlMappingBuilder builder = Yaml.createYamlMappingBuilder();

        for (Method method : clazz.getMethods()) {
            if (method.getParameterCount() != 0) throw new TooManyArgsFailure(method.getName());

            String key = getKey(method);

            builder = builder.add(
                    key,
                    serializers.serialize(
                            new RequestImpl(
                                    method.getReturnType(),
                                    method.getGenericReturnType(),
                                    key
                            ),
                            new ProxyForwarder(
                                    method, object
                            ).invoke()
                    )
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

}
