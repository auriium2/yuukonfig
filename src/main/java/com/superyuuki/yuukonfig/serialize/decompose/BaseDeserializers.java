package com.superyuuki.yuukonfig.serialize.decompose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.error.NoDeserializerFailure;
import com.superyuuki.yuukonfig.serialize.InitialRequestCtx;
import com.superyuuki.yuukonfig.serialize.RequestContext;

import java.util.*;

public class BaseDeserializers implements Deserializers {

    private final List<Deserializer> deserializers;

    public BaseDeserializers(List<Deserializer> deserializers) {
        this.deserializers = deserializers;
    }

    @Override
    public <T> T deserialize(YamlNode node, RequestContext<T> rq, DeserializerContext ctx) {
        Class<? extends T> requested = rq.requestedClass();

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
    public <T> T deserialize(YamlNode node, Class<T> rq, String configName) {
        return deserialize(node, new InitialRequestCtx<T>(rq), new BaseDeserializerCtx(this, configName));
    }
}
