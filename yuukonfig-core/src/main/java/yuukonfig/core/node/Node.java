package yuukonfig.core.node;



import yuukonfig.core.err.BadValueException;
import xyz.auriium.yuukonstants.GenericPath;

/**
 * Represents a node
 */
public interface Node {

    enum Type {
        SCALAR,
        MAPPING,
        SEQUENCE,
        NOT_PRESENT;
    }

    boolean isEmpty();
    Type type();

    Scalar asScalar() throws BadValueException, ClassCastException;
    Mapping asMapping() throws BadValueException, ClassCastException;
    Sequence asSequence() throws BadValueException, ClassCastException;

    <T> T rawAccess(Class<T> clazz) throws ClassCastException; //hackery

    GenericPath path();



}
