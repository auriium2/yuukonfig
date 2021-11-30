package com.superyuuki.yuukonfig.inbuilt.number;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.inbuilt.ScalarSerializer;
import com.superyuuki.yuukonfig.request.Request;

public class IntegerSerializer implements ScalarSerializer {

    @Override
    public int handles(Class<?> clazz) {
        if (clazz.equals(Integer.class) || clazz.equals(int.class)) return Priority.PRIORITY_HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public YamlNode serializeDefault(Request request, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine("0").buildPlainScalar("Default value - please change!");
    }

}
