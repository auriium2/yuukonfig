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

public class BooleanManipulator implements ManipulatorSafe<Boolean> {


    final Manipulation manipulation;
    final RawNodeFactory factory;

    public BooleanManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.factory = factory;
    }


    @Override
    public Boolean deserialize(Node node, GenericPath exceptionalKey) throws BadValueException {
        try {
            return Boolean.parseBoolean(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new BadValueException(
                    "the value is not a valid boolean",
                    "set the value to either true or false",
                    manipulation.configName(),
                    exceptionalKey
            );
        }
    }

    @Override
    public Node serializeObject(Boolean object, String[] comment) {
        return factory.scalarOf(
                object,
                comment
        );
    }

    @Override
    public Node serializeDefault(String[] comment) {
        return factory.scalarOf(
                false,
                "(default bool)",
                comment
        );
    }
}
