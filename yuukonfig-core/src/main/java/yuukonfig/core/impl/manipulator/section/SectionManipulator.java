package yuukonfig.core.impl.manipulator.section;

import xyz.auriium.yuukonstants.GenericPath;
import yuukonfig.core.annotate.Key;
import yuukonfig.core.annotate.Section;
import yuukonfig.core.err.BadValueException;
import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.Priority;
import yuukonfig.core.node.Mapping;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SectionManipulator implements Manipulator {

    final BaseManipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;

    public SectionManipulator(BaseManipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
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
    public Object deserialize(Node node) {
        Mapping mapping = node.asMapping();

        Map<String, Object> backingMap = new HashMap<>();

        for (Method method : useClass.getMethods()) {
            checkArgs(method);

            String key = getKey(method);
            Node possiblyNotPresent = mapping.valuePossiblyMissing(key);


            if (possiblyNotPresent.type() == Node.Type.NOT_PRESENT) {
                //TODO shitty hack 3

                if (method.getReturnType() != Optional.class) {
                    throw new BadValueException(
                            "a value is expected at this position, but none could be found!",
                            "add some data under this value!",
                            manipulation.configName(),
                            node.path().append(key)
                    );
                }
            }

            Class<?> returnType = method.getReturnType();

            var object = manipulation.deserialize(
                    possiblyNotPresent,
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
    public Node serializeObject(Object object, GenericPath path)  {
        RawNodeFactory.MappingBuilder builder = factory.makeMappingBuilder(path);

        for (Method method : useClass.getMethods()) {
            checkArgs(method);

            String key = getKey(method);
            Class<?> as = method.getReturnType();
            Object toSerialize = new ProxyForwarder(method, object).invoke(); //get the return of the method





            Node serialized = manipulation.serializeCtx(
                    toSerialize,
                    as,
                    path.append(key),
                    Contextual.present(
                            method.getGenericReturnType()
                    )
            );

            builder.add(key, serialized);

        }

        return builder.build();
    }

    @Override
    public Node serializeDefault(GenericPath path) {
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

        RawNodeFactory.MappingBuilder builder = factory.makeMappingBuilder(path);

        for (Method method : useClass.getMethods()) {
            checkArgs(method);

            Class<?> returnType = method.getReturnType();
            String key = getKey(method);
            Node serialized;

            //System.out.println("working on: " + key);

            if (method.isDefault()) {

                //System.out.println("defaulting: " + key);
                serialized = manipulation.serializeCtx(
                        new ProxyForwarder(method, proxy).invoke(),
                        returnType,
                        path.append(key),
                        Contextual.present(method.getGenericReturnType())
                );
            } else {
                serialized = manipulation.serializeDefaultCtx(
                        returnType,
                        path.append(key),
                        Contextual.present(method.getGenericReturnType())
                );
            }

            //System.out.println("finished: " + key + " : " + serialized);

            builder.add(key, serialized);
        }

        return builder.build();
    }



    void checkArgs(Method method) {
        if (method.getParameterCount() != 0) {
            throw new IllegalStateException(
                    String.format("The config interface method '%s' cannot have arguments!", method.getName())
            );
        }
    }


    String getKey(Method method) {
        if (method.isAnnotationPresent(Key.class)) {
            return method.getAnnotation(Key.class).value();
        }

        return method.getName();
    }
}
