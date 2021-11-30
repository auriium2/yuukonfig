package com.superyuuki.yuukonfig.impl.decompose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.decompose.Deserializer;
import com.superyuuki.yuukonfig.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.decompose.Deserializers;
import com.superyuuki.yuukonfig.request.Request;
import com.superyuuki.yuukonfig.request.UserRequest;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BaseDeserializers implements Deserializers {

    private final List<Deserializer> deserializers;

    public BaseDeserializers(List<Deserializer> deserializers) {
        this.deserializers = deserializers;
    }

    @Override
    public <T> T deserializeTyped(YamlNode node, UserRequest<T> rq, DeserializerContext ctx) {
        Class<T> requested = rq.typedRequestedClass();

        Optional<Deserializer> des = deserializers.stream().max(Comparator.comparing(v -> v.handles(requested)));

        if (des.isPresent()) {
            Deserializer deserializer = des.get();
            if (deserializer.handles(requested) > 0) {

                return requested.cast(deserializer.deserialize(node, rq, ctx));
            }
        }

        throw new NoDeserializerFailure(requested);
    }

    @Override
    public Object deserialize(YamlNode node, Request rq, DeserializerContext ctx) {
        Class<?> requested = rq.requestedClass();

        Optional<Deserializer> des = deserializers.stream().max(Comparator.comparing(v -> v.handles(requested)));

        if (des.isPresent()) {
            Deserializer deserializer = des.get();
            if (deserializer.handles(requested) > 0) {

                return deserializer.deserialize(node, rq, ctx);
            }
        }

        throw new NoDeserializerFailure(requested);
    }
}
