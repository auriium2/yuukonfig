package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.impl.TypeUtil;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.Priority;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonstants.GenericPath;

import java.lang.reflect.Type;
import java.util.Optional;

public class OptionalManipulator implements Manipulator {

    final Type toLookAt;
    final Contextual<Type> ctx;
    final BaseManipulation manipulation;
    final RawNodeFactory factory;

    public OptionalManipulator(BaseManipulation manipulation, Class<?> aClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
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
    public Object deserialize(Node node) throws BadValueException {
        if (node.type() == Node.Type.NOT_PRESENT) return Optional.empty();

        return Optional.of(manipulation.deserialize(node, TypeUtil.parameterizedClassFromType(ctx, manipulation)));
    }

    @Override
    public Node serializeObject(Object object, GenericPath path) {
        Optional<?> optional = (Optional<?>) object;

        if (optional.isPresent()) {
            return manipulation.serialize(object, TypeUtil.parameterizedClassFromType(ctx, manipulation), path);
        } else {
            return factory.notPresentOf(path);
        }
    }

    @Override
    public Node serializeDefault(GenericPath path) {
        return factory.notPresentOf(path);
    }
}
