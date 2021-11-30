package com.superyuuki.yuukonfig.impl.compose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.compose.Serializer;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.request.Request;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BaseSerializers implements Serializers {

    private final List<Serializer> serializers;

    public BaseSerializers(List<Serializer> serializers) {
        this.serializers = serializers;
    }

    @Override
    public YamlNode serialize(Request rq, Object object) {
        Class<?> clazz = rq.requestedClass();

        Optional<Serializer> ser = serializers.stream().max(Comparator.comparing(v -> v.handles(clazz)));

        if (ser.isPresent()) {
            Serializer serializer = ser.get();
            if (serializer.handles(clazz) > 0) {

                return serializer.serializeObject(rq, object, this);
            }
        }

        throw new NoSerializerFailure(clazz);
    }

    @Override
    public YamlNode serializeDefault(Request ctx) {
        Class<?> clazz = ctx.requestedClass();

        Optional<Serializer> ser = serializers.stream().max(Comparator.comparing(v -> v.handles(clazz)));

        if (ser.isPresent()) {
            Serializer serializer = ser.get();
            if (serializer.handles(clazz) > 0) {

                return serializer.serializeDefault(ctx, this);
            }
        }

        throw new NoSerializerFailure(clazz);
    }
}
