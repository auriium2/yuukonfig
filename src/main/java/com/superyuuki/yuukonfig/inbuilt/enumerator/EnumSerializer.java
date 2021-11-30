package com.superyuuki.yuukonfig.inbuilt.enumerator;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.compose.Serializer;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.impl.compose.NoDefaultSerializerFailure;
import com.superyuuki.yuukonfig.request.Request;

public class EnumSerializer implements Serializer {

    @Override
    public int handles(Class<?> clazz) {
        if (clazz.isEnum()) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public YamlNode serializeDefault(Request request, Serializers serializers) {
        throw new NoDefaultSerializerFailure(Enum.class);
    }

    @Override
    public YamlNode serializeObject(Request request, Object object, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine(((Enum<?>) object).name()).buildPlainScalar();
    }
}
