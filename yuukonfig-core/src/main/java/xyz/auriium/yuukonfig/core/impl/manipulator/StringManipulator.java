package xyz.auriium.yuukonfig.core.impl.manipulator;


import xyz.auriium.yuukonfig.core.manipulation.Contextual;
import xyz.auriium.yuukonfig.core.manipulation.Manipulation;
import xyz.auriium.yuukonfig.core.manipulation.Priority;
import xyz.auriium.yuukonfig.core.node.Node;
import xyz.auriium.yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonfig.core.manipulation.Manipulator;

import java.lang.reflect.Type;

public class StringManipulator implements Manipulator {


    final Manipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;

    public StringManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.useClass = useClass;
        this.factory = factory;
    }

    @Override
    public int handles() {
        if (useClass.equals(String.class)) return Priority.PRIORITY_HANDLE;
        if (useClass.equals(Character.class) || useClass.equals(char.class)) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(Node node, String exceptionalKey) {
        return node.asScalar().value();
    }

    @Override
    public Node serializeObject(Object object, String[] comment) {
        return factory.scalarOf(
                object.toString(),
                comment
        );
    }

    @Override
    public Node serializeDefault(String[] comment) {
        return factory.scalarOf(
                "",
                "(default string)",
                comment
        );
    }
}
