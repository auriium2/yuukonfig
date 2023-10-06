package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;

import yuukonfig.core.impl.safe.ManipulatorSafe;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulation;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.Priority;
import yuukonstants.GenericPath;

import java.lang.reflect.Type;

public class IntManipulator implements ManipulatorSafe<Integer> {

    final Manipulation manipulation;
    final RawNodeFactory factory;

    public IntManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.factory = factory;
    }


    @Override
    public Integer deserialize(Node node, GenericPath exceptionalKey) throws BadValueException {
        try {
            return Integer.parseInt(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new BadValueException(
                    "The value is not a valid integer",
                    "Set the value to an integer, like '2'",
                    manipulation.configName(),
                    exceptionalKey
            );
        }
    }

    @Override
    public Node serializeObject(Integer object, String[] comment) {

        return factory.scalarOf(
                object,
                comment
        );


    }

    @Override
    public Node serializeDefault(String[] comment) {
        return factory.scalarOf(
                0,
                "(default int)",
                comment
        );
    }
}
