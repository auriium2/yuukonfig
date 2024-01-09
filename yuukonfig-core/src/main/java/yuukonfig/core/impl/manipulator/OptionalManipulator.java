package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.impl.TypeUtil;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulation;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.Priority;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonstants.GenericPath;

import java.lang.reflect.Type;
import java.util.Optional;

public class OptionalManipulator implements Manipulator {

    final Type toLookAt;
    final Contextual<Type> ctx;
    final Manipulation manipulation;
    final RawNodeFactory factory;

    public OptionalManipulator(Manipulation manipulation, Class<?> aClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.toLookAt = aClass;
        this.ctx = typeContextual;
        this.manipulation = manipulation;
        this.factory = factory;
    }

    @Override
    public int handles() {
        return toLookAt == Optional.class ? Priority.HANDLE : Priority.DONT_HANDLE ;
    }

    @Override
    public Object deserialize(Node node, GenericPath path) throws BadValueException {
        if (node.type() == Node.Type.NOT_PRESENT) return Optional.empty();

        return Optional.of(manipulation.deserialize(node, path, TypeUtil.parameterizedClassFromType(ctx, manipulation)));
    }

    @Override
    public Node serializeObject(Object object, String[] comment) {
        Optional<?> optional = (Optional<?>) object;

        if (optional.isPresent()) {
            return manipulation.serialize(object, TypeUtil.parameterizedClassFromType(ctx, manipulation), comment);
        } else {
            return factory.notPresentOf();
        }
    }

    @Override
    public Node serializeDefault(String[] comment) {
        return factory.notPresentOf();
    }
}
