package com.superyuuki.yuukonfig;

import com.superyuuki.yuukonfig.compose.*;
import com.superyuuki.yuukonfig.inbuilt.bool.BooleanDeserializer;
import com.superyuuki.yuukonfig.inbuilt.bool.BooleanSerializer;
import com.superyuuki.yuukonfig.inbuilt.enumerator.EnumDeserializer;
import com.superyuuki.yuukonfig.inbuilt.enumerator.EnumSerializer;
import com.superyuuki.yuukonfig.inbuilt.number.DoubleDeserializer;
import com.superyuuki.yuukonfig.inbuilt.number.DoubleSerializer;
import com.superyuuki.yuukonfig.inbuilt.number.IntegerDeserializer;
import com.superyuuki.yuukonfig.inbuilt.number.IntegerSerializer;
import com.superyuuki.yuukonfig.inbuilt.text.StringDeserializer;
import com.superyuuki.yuukonfig.inbuilt.text.StringSerializer;
import com.superyuuki.yuukonfig.compose.reflection.InterfaceSerializer;
import com.superyuuki.yuukonfig.decompose.*;

import java.util.ArrayList;
import java.util.List;

public class BaseRegistry implements CommonRegistry {

    private final List<Serializer> serializers;
    private final List<Deserializer> deserializers;

    BaseRegistry(List<Serializer> serializers, List<Deserializer> deserializers) {
        this.serializers = serializers;
        this.deserializers = deserializers;
    }

    public static BaseRegistry defaults() {
        List<Serializer> serializers = new ArrayList<>();
        List<Deserializer> deserializers = new ArrayList<>();

        //base
        serializers.add(new InterfaceSerializer());
        deserializers.add(new InterfaceDeserializer());

        //primitives
        serializers.add(new TypedSerializer.Mock<>(Boolean.class, new BooleanSerializer()));
        serializers.add(new TypedSerializer.Mock<>(Integer.class, new IntegerSerializer()));
        serializers.add(new TypedSerializer.Mock<>(Double.class, new DoubleSerializer()));
        serializers.add(new TypedSerializer.Mock<>(String.class, new StringSerializer()));

        deserializers.add(new TypedDeserializer.Mock<>(Boolean.class, new BooleanDeserializer()));
        deserializers.add(new TypedDeserializer.Mock<>(Integer.class, new IntegerDeserializer()));
        deserializers.add(new TypedDeserializer.Mock<>(Double.class, new DoubleDeserializer()));
        deserializers.add(new TypedDeserializer.Mock<>(String.class, new StringDeserializer()));

        //enum
        serializers.add(new EnumSerializer());
        deserializers.add(new EnumDeserializer());

        return new BaseRegistry(serializers, deserializers);
    }

    @Override
    public void register(Serializer serializer, Deserializer deserializer) {
        serializers.add(serializer);
        deserializers.add(deserializer);
    }

    @Override
    public Deserializers makeDeserializers() {
        return new BaseDeserializers(deserializers);
    }

    @Override
    public Serializers makeSerializers() {
        return new BaseSerializers(serializers);
    }
}
