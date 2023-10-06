package yuukonfig.toml;

import yuukonfig.core.err.BadConfigException;
import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.Mapping;
import yuukonfig.core.node.Scalar;
import yuukonfig.core.node.Sequence;

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
        throw new BadConfigException(
                "toml",
                "code tried to convert a scalar to a mapping but that is not possible",
                "do not call asMapping()"
        );
    }

    @Override
    public Sequence asSequence() throws BadValueException, ClassCastException {
        throw new BadConfigException(
                "toml",
                "code tried to convert a scalar to a sequence but that is not possible",
                "do not call asSequence()"
        );
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws ClassCastException {
        if (clazz == Object.class) {
            return clazz.cast(wrapped);
        }

        throw new ClassCastException("is: a toml");
    }

    @Override
    public String value() {
        return wrapped.toString();
    }
}
