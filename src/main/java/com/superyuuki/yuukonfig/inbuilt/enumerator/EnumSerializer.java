package com.superyuuki.yuukonfig.inbuilt.enumerator;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.serialize.compose.Serializer;
import com.superyuuki.yuukonfig.serialize.compose.Serializers;
import com.superyuuki.yuukonfig.error.NoDefaultSerializerFailure;
import com.superyuuki.yuukonfig.error.UnexpectedRequestFailure;

public class EnumSerializer implements Serializer {

    @Override
    public int handles(Class<?> clazz) {
        if (clazz.isEnum()) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public YamlNode serializeDefault(Class<?> request, Serializers serializers) {

        throw new NoDefaultSerializerFailure(Enum.class);
    }

    @Override
    public YamlNode serializeObject(Class<?> request, Object object, Serializers serializers) {
        if (!request.isEnum()) throw new UnexpectedRequestFailure(request, Enum.class);

        return Yaml.createYamlScalarBuilder().addLine(((Enum<?>) object).name()).buildPlainScalar();
    }
}
