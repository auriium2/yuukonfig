package com.superyuuki.yuukonfig;

import com.superyuuki.yuukonfig.compose.Serializer;
import com.superyuuki.yuukonfig.decompose.Deserializer;

public interface CommonRegistry {

    void register(Serializer serializer, Deserializer deserializer);

}
