package yuukonfig.core.impl.manipulator;


import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Priority;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonfig.core.manipulation.Manipulator;
import xyz.auriium.yuukonstants.GenericPath;

import java.lang.reflect.Type;

public class StringManipulator implements Manipulator {


    final BaseManipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;

    public StringManipulator(BaseManipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
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
    public Object deserialize(Node node) {
        return node.asScalar().value();
    }

    @Override
    public Node serializeObject(Object object, GenericPath path) {
        return factory.scalarOf(
                path,
                object.toString()
        );
    }

    @Override
    public Node serializeDefault(GenericPath path) {
        return factory.scalarOf(
                path,
                ""
        );
    }
}
