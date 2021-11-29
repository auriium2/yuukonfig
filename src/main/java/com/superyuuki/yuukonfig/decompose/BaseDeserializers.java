package com.superyuuki.yuukonfig.decompose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.error.NoDeserializerFailure;
import com.superyuuki.yuukonfig.error.NoSerializerFailure;

import java.util.*;

public class BaseDeserializers implements Deserializers {

    private final List<Deserializer> deserializers;

    public BaseDeserializers(List<Deserializer> deserializers) {
        this.deserializers = deserializers;
    }

    @Override
    public <T> T deserialize(YamlNode node, Class<? extends T> clazz) {

        Optional<Deserializer> des = deserializers.stream().max(Comparator.comparing(v -> v.handles(clazz)));

        if (des.isPresent()) {
            Deserializer deserializer = des.get();
            if (deserializer.handles(clazz) > 0) {

                return clazz.cast(deserializer.deserialize(clazz, node, this));
            }
        }

        throw new NoDeserializerFailure(clazz);
    }
}
