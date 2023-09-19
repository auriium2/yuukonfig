package xyz.auriium.yuukonfig.core.impl.manipulator;

import xyz.auriium.yuukonfig.core.err.BadValueException;

import xyz.auriium.yuukonfig.core.node.Node;
import xyz.auriium.yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonfig.core.manipulation.Contextual;
import xyz.auriium.yuukonfig.core.manipulation.Manipulation;
import xyz.auriium.yuukonfig.core.manipulation.Manipulator;
import xyz.auriium.yuukonfig.core.manipulation.Priority;

import java.lang.reflect.Type;
import java.util.Arrays;

public class EnumManipulator implements Manipulator {

    final Manipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;

    public EnumManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
        this.useClass = useClass;
        this.manipulation = manipulation;
        this.factory = factory;
    }

    @Override
    public int handles() {
        if (useClass.isEnum()) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object deserialize(Node node, String exceptionalKey) throws BadValueException {
        String raw = node.asScalar().value();
        Class<Enum<?>> enumClass = (Class<Enum<?>>) useClass;

        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            String name = enumConstant.name();
            boolean equal = name.equalsIgnoreCase(raw);
            if (equal) {
                return enumConstant;
            }
        }
        throw new BadValueException(manipulation.configName(), exceptionalKey, String.format("%s is not valid! Supported values are: %s", raw, Arrays.toString(enumClass.getEnumConstants())));

    }

    @Override
    public Node serializeObject(Object object, String[] comment) {
        return factory.scalarOf(
                object.toString(),
                comment
        );
    }

    @Override
    public Node serializeDefault(String[] comment) {
        throw new IllegalStateException("no default value for enum type");
    }
}
