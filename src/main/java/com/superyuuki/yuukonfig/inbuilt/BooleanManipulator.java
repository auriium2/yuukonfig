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

public class BooleanManipulator implements Manipulator {


    private final Manipulation manipulation;
    private final Class<?> useClass;

    public BooleanManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual) {
        this.useClass = useClass;
        this.manipulation = manipulation;
    }

    @Override
    public int handles() {
        if (useClass.equals(Boolean.class) || useClass.equals(boolean.class)) return Priority.PRIORITY_HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(YamlNode node, String exceptionalKey) throws BadValueException {
        try {
            return Boolean.parseBoolean(node.asScalar().value());
        } catch (NumberFormatException exception) {
            throw new BadValueException(manipulation.configName(), exceptionalKey, "Value is not a boolean!");
        }
    }

    @Override
    public YamlNode serializeObject(Object object, String[] comment) {
        return Yaml.createYamlScalarBuilder().addLine(object.toString()).buildPlainScalar(Arrays.toString(comment));
    }

    @Override
    public YamlNode serializeDefault(String[] comment) {
        return Yaml.createYamlScalarBuilder().addLine("false").buildPlainScalar(Arrays.asList(comment), "(Default Boolean)");
    }
}
