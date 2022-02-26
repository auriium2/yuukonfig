package com.superyuuki.yuukonfig.inbuilt;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.BadValueException;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.manipulation.Contextual;
import com.superyuuki.yuukonfig.manipulation.Manipulation;
import com.superyuuki.yuukonfig.manipulation.Manipulator;
import com.superyuuki.yuukonfig.manipulation.Priority;

import java.lang.reflect.Type;
import java.util.Arrays;

public class EnumManipulator implements Manipulator {

    private final Manipulation manipulation;
    private final Class<?> useClass;

    public EnumManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual) {
        this.useClass = useClass;
        this.manipulation = manipulation;
    }

    @Override
    public int handles() {
        if (useClass.isEnum()) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object deserialize(YamlNode node, String exceptionalKey) throws BadValueException {
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
    public YamlNode serializeObject(Object object, String[] comment) {
        return Yaml.createYamlScalarBuilder().addLine(((Enum<?>) object).name()).buildPlainScalar();
    }

    @Override
    public YamlNode serializeDefault(String[] comment) {
        throw new IllegalStateException("no default value for enum type");
    }
}
