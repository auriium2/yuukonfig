package com.superyuuki.yuukonfig.inbuilt.text;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.inbuilt.ScalarSerializer;
import com.superyuuki.yuukonfig.request.Request;

public class StringSerializer implements ScalarSerializer {

    @Override
    public int handles(Class<?> clazz) {
        if (clazz.equals(String.class)) return Priority.PRIORITY_HANDLE;
        if (clazz.equals(Character.class) || clazz.equals(char.class)) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public YamlNode serializeDefault(Request request, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine("").buildPlainScalar("Default value - please change!"); //TODO customizable empty text
    }

}
