package xyz.auriium.yuukonfig.toml;

import com.moandjiezana.toml.Toml;
import xyz.auriium.yuukonfig.core.err.BadConfigException;
import xyz.auriium.yuukonfig.core.err.BadValueException;
import xyz.auriium.yuukonfig.core.node.Mapping;
import xyz.auriium.yuukonfig.core.node.Scalar;
import xyz.auriium.yuukonfig.core.node.Sequence;

public class TomlScalar implements Scalar {

    final Object wrapped;

    public TomlScalar(Object wrapped) {
        this.wrapped = wrapped;
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
        throw new BadConfigException("no");
    }

    @Override
    public Sequence asSequence() throws BadValueException, ClassCastException {
        throw new BadConfigException("no");
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws ClassCastException {
        throw new ClassCastException("is: a toml");
    }

    @Override
    public String value() {
        return wrapped.toString();
    }
}
