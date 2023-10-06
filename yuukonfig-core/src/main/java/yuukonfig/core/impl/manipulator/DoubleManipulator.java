package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;

import yuukonfig.core.impl.safe.ManipulatorSafe;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulation;
import yuukonfig.core.manipulation.Priority;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonfig.core.manipulation.Manipulator;
import yuukonstants.GenericPath;

import java.lang.reflect.Type;

public class DoubleManipulator implements ManipulatorSafe<Double> {

    final Manipulation manipulation;
    final RawNodeFactory factory;

    public DoubleManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.factory = factory;
    }

    @Override
    public Double deserialize(Node node, GenericPath exceptionalKey) throws BadValueException {
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
    public Node serializeObject(Double object, String[] comment) {
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
