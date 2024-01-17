package yuukonfig.core.node;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.err.Exceptions;
import xyz.auriium.yuukonstants.GenericPath;

public class NotPresentNode implements Node {

    final GenericPath path;

    public NotPresentNode(GenericPath path) {
        this.path = path;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Node.Type type() {
        return Node.Type.NOT_PRESENT;
    }

    @Override
    public Scalar asScalar() throws BadValueException, ClassCastException {
        throw Exceptions.UNEXPECTED_NODE_TYPE(path, Type.SCALAR, Type.MAPPING);
    }

    @Override
    public Mapping asMapping() throws BadValueException, ClassCastException {
        throw Exceptions.INCORRECT_NODE_TYPE_SERIALIZATION;
    }

    @Override
    public Sequence asSequence() throws BadValueException, ClassCastException {
        throw Exceptions.UNEXPECTED_NODE_TYPE(path, Type.SEQUENCE, Type.MAPPING);
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws ClassCastException {
        throw Exceptions.INCORRECT_NODE_TYPE_SERIALIZATION;
    }

    @Override
    public GenericPath path() {
        return path;
    }
}
