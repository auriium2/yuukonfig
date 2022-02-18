package com.superyuuki.yuukonfig.impl;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.BadValueException;
import com.superyuuki.yuukonfig.manipulation.Contextual;
import com.superyuuki.yuukonfig.manipulation.Manipulation;
import com.superyuuki.yuukonfig.manipulation.Manipulator;
import com.superyuuki.yuukonfig.manipulation.ManipulatorConstructor;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BaseManipulation implements Manipulation {

    private final List<ManipulatorConstructor> manips;
    private final String configName;

    public BaseManipulation(List<ManipulatorConstructor> manips, String configName) {
        this.manips = manips;
        this.configName = configName;
    }

    @Override
    public String configName() {
        return configName;
    }

    @Override
    public YamlNode serialize(Object object, String[] comment, Contextual<Type> typeCtx) {
        Class<?> as = object.getClass();
        ManipulatorConstructor ctor = search(as, typeCtx).orElseThrow(() -> new IllegalStateException("No such manipulator for type: " + as.getCanonicalName()));
        Manipulator manipulator = ctor.construct(this, as, typeCtx);

        return manipulator.serializeObject(object, comment);
    }

    @Override
    public YamlNode serializeDefault(Class<?> type, String[] comment, Contextual<Type> typeCtx) {
        ManipulatorConstructor ctor = search(type, typeCtx).orElseThrow(() -> new IllegalStateException("No such manipulator for type: " + type.getCanonicalName()));
        Manipulator manipulator = ctor.construct(this, type, typeCtx);

        return manipulator.serializeDefault(comment);
    }

    @Override
    public Object deserialize(YamlNode node, String key, Class<?> as, Contextual<Type> typeCtx) throws BadValueException {
        ManipulatorConstructor ctor = search(as, typeCtx).orElseThrow(() -> new IllegalStateException("No such manipulator for type: " + as.getCanonicalName()));
        Manipulator manipulator = ctor.construct(this, as, typeCtx);

        return manipulator.deserialize(node, key);
    }

    Optional<ManipulatorConstructor> search(Class<?> as, Contextual<Type> typeCtx) {
        return manips
                .stream()
                .max(
                        Comparator.comparing(v ->
                                v
                                        .construct(this, as, typeCtx)
                                        .handles()
                        )
                );
    }
}
