package yuukonfig.core.impl;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.ManipulatorConstructor;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonstants.GenericPath;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class BaseManipulation {

    final RawNodeFactory factory;
    final ManipulatorConstructor[] manips;
    final String configName;
    final String fullConfigName;

    public BaseManipulation(RawNodeFactory factory, ManipulatorConstructor[] manips, String configName, String fullConfigName) {
        this.factory = factory;
        this.manips = manips;
        this.configName = configName;
        this.fullConfigName = fullConfigName;
    }

    public String configName() {
        return configName;
    }

    public String fullConfigName() {
        return fullConfigName;
    }

    public Node serialize(Object dataToSerialize, Class<?> serializeAs, GenericPath path) {
        return serializeCtx(dataToSerialize, serializeAs, path, Contextual.empty());
    }

    public Node serializeDefaultCtx(Class<?> serializeAs, GenericPath path) {
        return serializeDefaultCtx(serializeAs, path, Contextual.empty());
    }

    public <T> T safeDeserialize(Node node, Class<T> as, Contextual<Type> typeCtx) {
        var obj = deserialize(node, as, typeCtx);
        //TODO shitty hack

        if (obj instanceof Long && as == long.class) {
            long totallyLittleDouble = (Long) obj;
            return as.cast(totallyLittleDouble);
        }

        if (obj instanceof Short && as == short.class) {
            short totallyLittleDouble = (Short) obj;
            return as.cast(totallyLittleDouble);
        }

        if (obj instanceof Boolean && as == boolean.class) {
            boolean totallyLittleDouble = (Boolean) obj;
            return as.cast(totallyLittleDouble);
        }

        if (obj instanceof Character && as == char.class) {
            double totallyLittleDouble = (Character) obj;
            return as.cast(totallyLittleDouble);
        }


        if (obj instanceof Double && as == double.class) {
            double totallyLittleDouble = (Double) obj;
            return as.cast(totallyLittleDouble);
        }

        if (obj instanceof Integer && as == int.class) {
            int totallyLittleDouble = (Integer) obj;
            return as.cast(totallyLittleDouble);
        }

        if (obj instanceof Byte && as == byte.class) {
            byte totallyLittleDouble = (Byte) obj;
            return as.cast(totallyLittleDouble);
        }


        return as.cast(obj);
    }

    public <T> T safeDeserialize(Node node, Class<T> as) {
        return safeDeserialize(node, as, Contextual.empty());
    }

    public Object deserialize(Node node, Class<?> as) {
        return deserialize(node, as, Contextual.empty());
    }


    public Node serializeCtx(Object object, Class<?> serializeAs, GenericPath path, Contextual<Type> typeCtx) {
        ManipulatorConstructor ctor = search(serializeAs, typeCtx).orElseThrow(() -> new IllegalStateException("No such manipulator for type: " + serializeAs.getCanonicalName()));
        Manipulator manipulator = ctor.construct(this, serializeAs, typeCtx, factory);

        return manipulator.serializeObject(object, path);
    }

    public Node serializeDefaultCtx(Class<?> serializeAs, GenericPath path, Contextual<Type> typeCtx) {
        ManipulatorConstructor ctor = search(serializeAs, typeCtx).orElseThrow(() -> new IllegalStateException("No such manipulator for type: " + serializeAs.getCanonicalName()));
        Manipulator manipulator = ctor.construct(this, serializeAs, typeCtx, factory);

        return manipulator.serializeDefault(path);
    }

    public Object deserialize(Node node, Class<?> as, Contextual<Type> typeCtx) throws BadValueException {
        ManipulatorConstructor ctor = search(as, typeCtx).orElseThrow(() -> new IllegalStateException("No such manipulator for type: " + as.getCanonicalName()));
        Manipulator manipulator = ctor.construct(this, as, typeCtx, factory);

        return manipulator.deserialize(node);
    }

    Optional<ManipulatorConstructor> search(Class<?> as, Contextual<Type> typeCtx) {
        return Arrays.stream(manips)
                .max(
                        Comparator.comparing(v ->
                                v
                                        .construct(this, as, typeCtx, factory)
                                        .handles()
                        )
                ).flatMap(ctor -> {
                    if (ctor.construct(this, as, typeCtx, factory).handles() > 0) {
                        return Optional.of(ctor);
                    }

                    return Optional.empty();
                });
    }
}
