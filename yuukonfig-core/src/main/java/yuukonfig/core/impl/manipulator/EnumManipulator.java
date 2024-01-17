package yuukonfig.core.impl.manipulator;

import yuukonfig.core.Exceptions;
import yuukonfig.core.err.BadValueException;

import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.Priority;
import xyz.auriium.yuukonstants.GenericPath;

import java.lang.reflect.Type;
import java.util.Arrays;

public class EnumManipulator implements Manipulator {

    final BaseManipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;

    public EnumManipulator(BaseManipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
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
    public Object deserialize(Node node) throws BadValueException {
        String raw = node.asScalar().value();
        Class<Enum<?>> enumClass = (Class<Enum<?>>) useClass;

        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            String name = enumConstant.name();
            boolean equal = name.equalsIgnoreCase(raw);
            if (equal) {
                return enumConstant;
            }
        }
        throw new BadValueException(
                manipulation.configName(),
                String.format("The type %s is not a valid enum in the enum set %s", raw, enumClass.getSimpleName()),
                String.format("Switch to a valid enum, one of: %s", Arrays.toString(enumClass.getEnumConstants())),
                node.path()
        );

    }

    @Override
    public Node serializeObject(Object object, GenericPath path) {
        return factory.scalarOf(
                path,
                object
        );
    }

    @Override
    public Node serializeDefault(GenericPath path) {
        throw Exceptions.NO_VALID_DEFAULT(path, useClass.getSimpleName());
    }
}
