package com.superyuuki.yuukonfig;

import com.superyuuki.yuukonfig.serialize.compose.Serializer;
import com.superyuuki.yuukonfig.serialize.compose.Serializers;
import com.superyuuki.yuukonfig.serialize.decompose.Deserializer;
import com.superyuuki.yuukonfig.serialize.decompose.Deserializers;

public interface CommonRegistry {

    void register(Serializer serializer, Deserializer deserializer);

    Deserializers makeDeserializers();
    Serializers makeSerializers();

}
