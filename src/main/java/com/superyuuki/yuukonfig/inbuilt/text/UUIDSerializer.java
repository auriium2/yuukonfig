package com.superyuuki.yuukonfig.inbuilt.text;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.impl.compose.NoDefaultSerializerFailure;
import com.superyuuki.yuukonfig.inbuilt.ScalarSerializer;
import com.superyuuki.yuukonfig.request.Request;

import java.util.UUID;

public class UUIDSerializer implements ScalarSerializer {
    @Override
    public int handles(Class<?> clazz) {
        if (clazz.equals(UUID.class)) return Priority.PRIORITY_HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public YamlNode serializeDefault(Request rq, Serializers serializers) {
        throw new NoDefaultSerializerFailure(UUID.class);
    }
}
