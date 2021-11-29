package com.superyuuki.yuukonfig;

import com.superyuuki.yuukonfig.compose.Serializer;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.decompose.Deserializer;
import com.superyuuki.yuukonfig.decompose.Deserializers;

public interface CommonRegistry {

    void register(Serializer serializer, Deserializer deserializer);

    Deserializers makeDeserializers();
    Serializers makeSerializers();

}
