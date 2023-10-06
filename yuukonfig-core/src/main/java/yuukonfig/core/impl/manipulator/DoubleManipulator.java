package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;

import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulation;
import yuukonfig.core.manipulation.Priority;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonfig.core.manipulation.Manipulator;
import yuukonstants.GenericPath;

import java.lang.reflect.Type;

public class DoubleManipulator implements Manipulator {

    final Manipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;

    public DoubleManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.useClass = useClass;
        this.manipulation = manipulation;
        this.factory = factory;
    }

    @Override
    public int handles() {
        if (useClass.equals(Double.class) || useClass.equals(double.class)) return Priority.PRIORITY_HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(Node node, GenericPath exceptionalKey) throws BadValueException {
        try {
            return Double.parseDouble(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new BadValueException(
                    "the value is not a valid double",
                    "set the value to a number like '0.0d'",
                    manipulation.configName(),
                    exceptionalKey
            );
        }
    }

    @Override
    public Node serializeObject(Object object, String[] comment) {
        return factory.scalarOf(
                object,
                comment
        );

    }

    @Override
    public Node serializeDefault(String[] comment) {
        return factory.scalarOf(
                0.0d,
                "(default double)",
                comment
        );
    }
}
