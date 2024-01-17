package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;

import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.impl.safe.ManipulatorSafe;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonstants.GenericPath;

import java.lang.reflect.Type;

public class DoubleManipulator implements ManipulatorSafe<Double> {

    final BaseManipulation manipulation;
    final RawNodeFactory factory;

    public DoubleManipulator(BaseManipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.factory = factory;
    }

    @Override
    public Double deserialize(Node node) throws BadValueException {
        try {
            return Double.parseDouble(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new BadValueException(
                    "the value is not a valid double",
                    "set the value to a number like '0.0d'",
                    manipulation.configName(),
                    node.path()
            );
        }
    }

    @Override
    public Node serializeObject(Double object, GenericPath path) {
        return factory.scalarOf(
                path,
                object
                
        );

    }

    @Override
    public Node serializeDefault(GenericPath path) {
        return factory.scalarOf(
                path,
                0d
        );
    }
}
