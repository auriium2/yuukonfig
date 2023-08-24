package xyz.auriium.yuukonfig.core.impl.manipulator.section;

import xyz.auriium.yuukonfig.core.annotate.Comment;
import xyz.auriium.yuukonfig.core.annotate.Key;
import xyz.auriium.yuukonfig.core.annotate.Section;
import xyz.auriium.yuukonfig.core.err.BadConfigException;
import xyz.auriium.yuukonfig.core.manipulation.Contextual;
import xyz.auriium.yuukonfig.core.manipulation.Manipulation;
import xyz.auriium.yuukonfig.core.manipulation.Manipulator;
import xyz.auriium.yuukonfig.core.manipulation.Priority;
import xyz.auriium.yuukonfig.core.node.Mapping;
import xyz.auriium.yuukonfig.core.node.Node;
import xyz.auriium.yuukonfig.core.node.RawNodeFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SectionManipulator implements Manipulator {

    final Manipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;

    public SectionManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.useClass = useClass;
        this.factory = factory;
    }

    @Override
    public int handles() {
        if (Section.class.isAssignableFrom(useClass)) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(Node node, String exceptionalKey) {
        Mapping mapping = node.asMapping();

        Map<String, Object> backingMap = new HashMap<>();

        for (Method method : useClass.getMethods()) {
            checkArgs(method);

            String key = getKey(method);
            Node nullable = mapping.value(key);

            if (nullable == null) throw new BadConfigException("No YAML found for key: " + key);

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
    public Node serializeObject(Object object, String[] comment) {
        RawNodeFactory.MappingBuilder builder = factory.makeMappingBuilder();

        for (Method method : useClass.getMethods()) {
            checkArgs(method);

            String key = getKey(method);
            Class<?> as = method.getReturnType();
            String[] comments = getComment(method);
            Object toSerialize = new ProxyForwarder(method, object).invoke(); //get the return of the method





            Node serialized = manipulation.serialize(
                    toSerialize,
                    as,
                    comments,
                    Contextual.present(
                            method.getGenericReturnType()
                    )
            );

            builder.add(key, serialized);

        }

        return builder.build(comment);
    }

    @Override
    public Node serializeDefault(String[] comment) {
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

        RawNodeFactory.MappingBuilder builder = factory.makeMappingBuilder();

        for (Method method : useClass.getMethods()) {
            checkArgs(method);

            Class<?> returnType = method.getReturnType();
            String key = getKey(method);
            String[] comments = getComment(method);
            Node serialized;

            //System.out.println("working on: " + key);

            if (method.isDefault()) {

                //System.out.println("defaulting: " + key);
                serialized = manipulation.serialize(
                        new ProxyForwarder(method, proxy).invoke(),
                        returnType,
                        comments,
                        Contextual.present(method.getGenericReturnType())
                );
            } else {
                serialized = manipulation.serializeDefault(
                        returnType,
                        comments,
                        Contextual.present(method.getGenericReturnType())
                );
            }

            //System.out.println("finished: " + key + " : " + serialized);

            builder.add(key, serialized);
        }

        return builder.build(comment);
    }



    void checkArgs(Method method) {
        if (method.getParameterCount() != 0) {
            throw new IllegalStateException(
                    String.format("The config interface method '%s' cannot have arguments!", method.getName())
            );
        }
    }

    String[] getComment(Method method) {
        if (method.isAnnotationPresent(Comment.class)) {
            return method.getAnnotation(Comment.class).value();
        }

        return new String[]{};
    }

    String getKey(Method method) {
        if (method.isAnnotationPresent(Key.class)) {
            return method.getAnnotation(Key.class).value();
        }

        return method.getName();
    }
}
