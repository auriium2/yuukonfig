package com.superyuuki.yuukonfig.inbuilt;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.YuuKonfigAPI;
import com.superyuuki.yuukonfig.manipulation.Contextual;
import com.superyuuki.yuukonfig.manipulation.Manipulation;
import com.superyuuki.yuukonfig.manipulation.Manipulator;
import com.superyuuki.yuukonfig.manipulation.Priority;

import java.lang.reflect.Type;
import java.util.Arrays;

public class StringManipulator implements Manipulator {

    static {
        YuuKonfig.instance().register(StringManipulator::new);
    }

    private final Class<?> useClass;

    public StringManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual) {
        this.useClass = useClass;
    }

    @Override
    public int handles() {
        if (useClass.equals(String.class)) return Priority.PRIORITY_HANDLE;
        if (useClass.equals(Character.class) || useClass.equals(char.class)) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(YamlNode node, String exceptionalKey) {
        return node.asScalar().value();
    }

    @Override
    public YamlNode serializeObject(Object object, String[] comment) {
        return Yaml.createYamlScalarBuilder().addLine(object.toString()).buildPlainScalar(Arrays.asList(comment), "");
    }

    @Override
    public YamlNode serializeDefault(String[] comment) {
        return Yaml.createYamlScalarBuilder().addLine("").buildPlainScalar(Arrays.asList(comment), "");
    }
}
