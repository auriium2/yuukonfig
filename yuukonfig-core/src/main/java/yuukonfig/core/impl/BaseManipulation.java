package yuukonfig.core.impl;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulation;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.ManipulatorConstructor;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonstants.GenericPath;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BaseManipulation implements Manipulation {

    final RawNodeFactory factory;
    final List<ManipulatorConstructor> manips;
    final String configName;

    public BaseManipulation(RawNodeFactory factory, List<ManipulatorConstructor> manips, String configName) {
        this.factory = factory;
        this.manips = manips;
        this.configName = configName;
    }

    @Override
    public String configName() {
        return configName;
    }

    @Override
    public Node serialize(Object object, Class<?> under, String[] comment, Contextual<Type> typeCtx) {
        ManipulatorConstructor ctor = search(under, typeCtx).orElseThrow(() -> new IllegalStateException("No such manipulator for type: " + under.getCanonicalName()));
        Manipulator manipulator = ctor.construct(this, under, typeCtx, factory);

        return manipulator.serializeObject(object, comment);
    }

    @Override
    public Node serializeDefault(Class<?> type, String[] comment, Contextual<Type> typeCtx) {
        ManipulatorConstructor ctor = search(type, typeCtx).orElseThrow(() -> new IllegalStateException("No such manipulator for type: " + type.getCanonicalName()));
        Manipulator manipulator = ctor.construct(this, type, typeCtx, factory);

        return manipulator.serializeDefault(comment);
    }

    @Override
    public Object deserialize(Node node, GenericPath key, Class<?> as, Contextual<Type> typeCtx) throws BadValueException {
        ManipulatorConstructor ctor = search(as, typeCtx).orElseThrow(() -> new IllegalStateException("No such manipulator for type: " + as.getCanonicalName()));
        Manipulator manipulator = ctor.construct(this, as, typeCtx, factory);

        return manipulator.deserialize(node, key);
    }

    Optional<ManipulatorConstructor> search(Class<?> as, Contextual<Type> typeCtx) {
        return manips
                .stream()
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
