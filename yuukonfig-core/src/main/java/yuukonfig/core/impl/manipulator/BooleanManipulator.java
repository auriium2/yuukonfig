package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.impl.safe.ManipulatorSafe;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonfig.core.manipulation.Contextual;
import xyz.auriium.yuukonstants.GenericPath;

import java.lang.reflect.Type;

public class BooleanManipulator implements ManipulatorSafe<Boolean> {


    final BaseManipulation manipulation;
    final RawNodeFactory factory;

    public BooleanManipulator(BaseManipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.factory = factory;
    }


    @Override
    public Boolean deserialize(Node node) throws BadValueException {
        try {
            return Boolean.parseBoolean(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new BadValueException(
                    "the value is not a valid boolean",
                    "set the value to either true or false",
                    manipulation.configName(),
                    node.path()
            );
        }
    }

    @Override
    public Node serializeObject(Boolean object, GenericPath path) {
        return factory.scalarOf(
                path,
                object
        );
    }

    @Override
    public Node serializeDefault(GenericPath path) {
        return factory.scalarOf(
                path,
                false
        );
    }
}
