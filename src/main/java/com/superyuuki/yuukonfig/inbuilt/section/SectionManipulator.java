package com.superyuuki.yuukonfig.inbuilt.section;

import com.amihaiemil.eoyaml.*;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.manipulation.Contextual;
import com.superyuuki.yuukonfig.manipulation.Priority;
import com.superyuuki.yuukonfig.user.Section;
import com.superyuuki.yuukonfig.user.ConfComment;
import com.superyuuki.yuukonfig.user.ConfKey;
import com.superyuuki.yuukonfig.manipulation.Manipulation;
import com.superyuuki.yuukonfig.manipulation.Manipulator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SectionManipulator implements Manipulator {

    static {
        YuuKonfig.instance().register(SectionManipulator::new);
    }

    private final Manipulation manipulation;
    private final Class<?> useClass;

    public SectionManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual) {
        this.manipulation = manipulation;
        this.useClass = useClass;
    }

    @Override
    public int handles() {
        if (Section.class.isAssignableFrom(useClass)) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(YamlNode node, String exceptionalKey) {
        YamlMapping mapping = node.asMapping();

        Map<String, Object> backingMap = new HashMap<>();

        for (Method method : useClass.getMethods()) {
            checkArgs(method);

            String key = getKey(method);
            YamlNode nullable = mapping.value(key);

            if (nullable == null) throw new YamlNodeNotFoundException("No YAML found for key: " + key);

            Class<?> returnType = method.getReturnType();

            var object = manipulation.deserialize(
                    nullable,
                    key,
                    returnType,
                    Contextual.present(
                            method.getGenericReturnType()
                    )
            );

            backingMap.put(method.getName(), object);
        }

        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{ useClass },
                new MappedInvocationHandler(Map.copyOf(backingMap))

        );
    }

    @Override
    public YamlNode serializeObject(Object object, String[] comment) {
        YamlMappingBuilder builder = Yaml.createYamlMappingBuilder();

        for (Method method : useClass.getMethods()) {
            checkArgs(method);

            String key = getKey(method);
            String[] comments = getComment(method);
            Object toSerialize = new ProxyForwarder(method, object).invoke(); //get the return of the method

            YamlNode serialized = manipulation.serialize(toSerialize, comments, Contextual.present(method.getGenericReturnType()));

            builder = builder.add(key, serialized);

        }

        return builder.build(Arrays.asList(comment));
    }

    @Override
    public YamlNode serializeDefault(String[] comment) {
        Object proxy = Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{ useClass },
                (a,b,c) -> {
                    if (b.isDefault()) {
                        return InvocationHandler.invokeDefault(a, b, c);
                    }

                    throw new IllegalStateException("Defaulting anonymous proxy does not support normal method queries!");
                }
        );

        YamlMappingBuilder builder = Yaml.createYamlMappingBuilder();

        for (Method method : useClass.getMethods()) {
            checkArgs(method);

            String key = getKey(method);
            String[] comments = getComment(method);
            YamlNode serialized;

            if (method.isDefault()) {
                serialized = manipulation.serialize(
                        new ProxyForwarder(method, proxy).invoke(),
                        comments,
                        Contextual.present(method.getGenericReturnType())
                );
            } else {
                serialized = manipulation.serializeDefault(
                        method.getReturnType(),
                        comments,
                        Contextual.present(method.getGenericReturnType())
                );
            }

            builder.add(key, serialized);
        }

        return builder.build(Arrays.asList(comment));
    }

    // utility //

    void checkArgs(Method method) {
        if (method.getParameterCount() != 0) {
            throw new IllegalStateException(
                    String.format("The config interface method %s cannot have arguments!", method.getName())
            );
        }
    }

    String[] getComment(Method method) {
        if (method.isAnnotationPresent(ConfComment.class)) {
            return method.getAnnotation(ConfComment.class).value();
        }

        return new String[]{};
    }

    String getKey(Method method) {
        if (method.isAnnotationPresent(ConfKey.class)) {
            return method.getAnnotation(ConfKey.class).value();
        }

        return method.getName();
    }
}
