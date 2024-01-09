package yuukonfig.core.node;



import yuukonfig.core.err.BadValueException;

/**
 * Exists to wrap YamlNode because.. fucking yamlnode bruh
 */
public interface Node {

    enum Type {
        SCALAR,
        MAPPING,
        STREAM,
        SEQUENCE,
        NOT_PRESENT;
    }

    boolean isEmpty();
    Type type();

    Scalar asScalar() throws BadValueException, ClassCastException;
    Mapping asMapping() throws BadValueException, ClassCastException;
    Sequence asSequence() throws BadValueException, ClassCastException;

    <T> T rawAccess(Class<T> clazz) throws ClassCastException; //hackery



}
