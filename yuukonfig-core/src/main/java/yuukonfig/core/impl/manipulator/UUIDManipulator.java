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
import java.util.UUID;

public class UUIDManipulator implements ManipulatorSafe<UUID> {

    final Manipulation manipulation;
    final RawNodeFactory factory;

    public UUIDManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.factory = factory;
    }


    @Override
    public UUID deserialize(Node node, GenericPath exceptionalKey) throws BadValueException {
        if (node.type() != Node.Type.SCALAR) {
            throw new BadValueException(
                    "the value is not a valid uuid and seems to be a collection",
                    "set the value to a uuid (look it up)",
                    manipulation.configName(),
                    exceptionalKey
            );
        }


        try {
            return UUID.fromString(node.asScalar().value());
        } catch (IllegalArgumentException exception) {

            throw new BadValueException(
                    "the value is not a valid uuid",
                    "set the value to a uuid (look it up)",
                    manipulation.configName(),
                    exceptionalKey
            );
        }
    }

    @Override
    public Node serializeObject(UUID object, String[] comment) {
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
