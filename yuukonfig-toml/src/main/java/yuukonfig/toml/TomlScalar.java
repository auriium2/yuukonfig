package yuukonfig.toml;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.err.Exceptions;
import yuukonfig.core.node.Mapping;
import yuukonfig.core.node.Scalar;
import yuukonfig.core.node.Sequence;
import xyz.auriium.yuukonstants.GenericPath;

public class TomlScalar implements Scalar {

    final Object wrapped;
    final GenericPath path;

    public TomlScalar(Object wrapped, GenericPath path) {
        this.wrapped = wrapped;
        this.path = path;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Type type() {
        return Type.SCALAR;
    }

    @Override
    public Scalar asScalar() throws BadValueException, ClassCastException {
        return this;
    }

    @Override
    public Mapping asMapping() throws BadValueException, ClassCastException {
        throw Exceptions.UNEXPECTED_NODE_TYPE(path, Type.MAPPING, Type.SCALAR);
    }

    @Override
    public Sequence asSequence() throws BadValueException, ClassCastException {
        throw Exceptions.UNEXPECTED_NODE_TYPE(path, Type.SEQUENCE, Type.SCALAR);
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws ClassCastException {
        if (clazz == Object.class) {
            return clazz.cast(wrapped);
        }

        throw new ClassCastException("is: a toml");
    }

    @Override
    public GenericPath path() {
        return path;
    }

    @Override
    public String value() {
        return wrapped.toString();
    }
}
