package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.impl.safe.ManipulatorSafe;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonstants.GenericPath;

import java.lang.reflect.Type;

public class IntManipulator implements ManipulatorSafe<Integer> {

    final BaseManipulation manipulation;
    final RawNodeFactory factory;

    public IntManipulator(BaseManipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.factory = factory;
    }


    @Override
    public Integer deserialize(Node node) throws BadValueException {
        try {
            return Integer.parseInt(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new BadValueException(
                    "The value is not a valid integer",
                    "Set the value to an integer, like '2'",
                    manipulation.configName(),
                    node.path()
            );
        }
    }

    @Override
    public Node serializeObject(Integer object, GenericPath path) {

        return factory.scalarOf(
                path,
                object
        );


    }

    @Override
    public Node serializeDefault(GenericPath path) {
        return factory.scalarOf(
                path,
                0
        );
    }
}
