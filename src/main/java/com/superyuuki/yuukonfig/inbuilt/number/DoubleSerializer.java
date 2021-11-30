package com.superyuuki.yuukonfig.inbuilt.number;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.inbuilt.ScalarSerializer;
import com.superyuuki.yuukonfig.request.Request;

public class DoubleSerializer implements ScalarSerializer {
    @Override
    public int handles(Class<?> clazz) {
        if (clazz.equals(Double.class) || clazz.equals(double.class)) return Priority.PRIORITY_HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public YamlNode serializeDefault(Request request, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine("0.0").buildPlainScalar("Default value - please change!");
    }


}
