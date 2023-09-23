package xyz.auriium.yuukonfig.core.impl.manipulator.section;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.*;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.*;
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

    static final ByteBuddy BUDDY = new ByteBuddy();

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Inner {

    }


    @Override
    public Object deserialize(Node node, String exceptionalKey) {
    /*    System.out.println(useClass.getName());
        System.out.println("using loader: " + useClass.getClassLoader().getName());
*/
        //if (useClass.isAnnotationPresent(Inner.class)) throw new IllegalStateException(useClass.getSimpleName() + "_" + useClass.getClassLoader().getName() );

        Mapping mapping = node.asMapping();

        Map<Method, Object> backingMap = new HashMap<>();

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

            backingMap.put(method, object);
        }

        try {

            //according to the bytebuddy site this generates class that could be 4x faster than prev impl

            //if (true) throw new IllegalStateException(useClass.getName());
            //in parallel classloader.. now what?


            var builder = BUDDY.subclass(useClass)
                    //default methods
                    .name(useClass.getPackageName() + "." + useClass.getSimpleName())
                    .suffix("Generated_" + Integer.toHexString(hashCode()));

            if (useClass.getEnclosingClass() != null) {
                //builder = builder.innerTypeOf(useClass.getEnclosingClass());
            }

            builder
                    .method(ElementMatchers.isEquals())
                    .intercept(EqualsMethod.isolated());
                   /* .method(ElementMatchers.isHashCode())
                    .intercept(HashCodeMethod.usingDefaultOffset());*/

            for (Map.Entry<Method, Object> values : backingMap.entrySet()) { //every method on the new implementation will only do one thing (return config value)
                builder = builder
                        .method(ElementMatchers.is(values.getKey()))
                        .intercept(FixedValue.value(values.getValue()));
            }

            DynamicType.Unloaded<?> unloaded = builder.make();

            ClassLoader loaderToUse = useClass.getClassLoader();

            //TODO I HAVE NO FUCKING CLUE WHY THIS WORKS ITS PROBABLY REALLY REALLY BAD LOL
            DynamicType.Loaded<?> loaded = unloaded.load(loaderToUse, ClassLoadingStrategy.ForBootstrapInjection.Default.INJECTION);

            return loaded
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Something terribly wrong has happened in YuuKonfig", e);
        }

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
