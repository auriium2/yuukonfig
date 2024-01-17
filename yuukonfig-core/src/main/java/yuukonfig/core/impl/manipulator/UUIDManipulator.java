package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;

import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.impl.safe.ManipulatorSafe;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonstants.GenericPath;

import java.lang.reflect.Type;
import java.util.UUID;

public class UUIDManipulator implements ManipulatorSafe<UUID> {

    final BaseManipulation manipulation;
    final RawNodeFactory factory;

    public UUIDManipulator(BaseManipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.factory = factory;
    }


    @Override
    public UUID deserialize(Node node) throws BadValueException {

        try {
            return UUID.fromString(node.asScalar().value());
        } catch (IllegalArgumentException exception) {

            throw new BadValueException(
                    "the value is not a valid uuid",
                    "set the value to a uuid (look it up)",
                    manipulation.configName(),
                    node.path()
            );
        }
    }

    @Override
    public Node serializeObject(UUID object, GenericPath path) {
        return factory.scalarOf(
                path,
                object
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
