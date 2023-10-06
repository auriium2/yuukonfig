package yuukonfig.yaml;

import com.amihaiemil.eoyaml.YamlNode;
import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.Mapping;
import yuukonfig.core.node.Scalar;
import yuukonfig.core.node.Sequence;

public class YamlScalarShiv implements Scalar {

    final com.amihaiemil.eoyaml.Scalar scalar;

    public YamlScalarShiv(com.amihaiemil.eoyaml.Scalar scalar) {
        this.scalar = scalar;
    }

    @Override
    public String value() {
        return scalar.value();
    }

    @Override
    public boolean isEmpty() {
        return scalar.isEmpty();
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
        throw new ClassCastException("this is a scalar");
    }

    @Override
    public Sequence asSequence() throws BadValueException, ClassCastException {
        throw new ClassCastException("this is a scalar");
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws ClassCastException {
        if (clazz == YamlNode.class) {
            return clazz.cast(scalar);
        }

        throw new ClassCastException("invalid access of unexpected type");

    }
}
