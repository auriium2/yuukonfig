package com.superyuuki.yuukonfig.impl.load;

import com.superyuuki.yuukonfig.CommonRegistry;
import com.superyuuki.yuukonfig.compose.Serializer;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.decompose.Deserializer;
import com.superyuuki.yuukonfig.decompose.Deserializers;
import com.superyuuki.yuukonfig.decompose.TypedDeserializer;
import com.superyuuki.yuukonfig.impl.compose.BaseSerializers;
import com.superyuuki.yuukonfig.impl.decompose.BaseDeserializers;
import com.superyuuki.yuukonfig.inbuilt.bool.BooleanDeserializer;
import com.superyuuki.yuukonfig.inbuilt.bool.BooleanSerializer;
import com.superyuuki.yuukonfig.inbuilt.collection.ListDeserializer;
import com.superyuuki.yuukonfig.inbuilt.collection.ListSerializer;
import com.superyuuki.yuukonfig.inbuilt.enumerator.EnumDeserializer;
import com.superyuuki.yuukonfig.inbuilt.enumerator.EnumSerializer;
import com.superyuuki.yuukonfig.inbuilt.number.DoubleDeserializer;
import com.superyuuki.yuukonfig.inbuilt.number.DoubleSerializer;
import com.superyuuki.yuukonfig.inbuilt.number.IntegerDeserializer;
import com.superyuuki.yuukonfig.inbuilt.number.IntegerSerializer;
import com.superyuuki.yuukonfig.inbuilt.section.SectionDeserializer;
import com.superyuuki.yuukonfig.inbuilt.section.SectionSerializer;
import com.superyuuki.yuukonfig.inbuilt.text.StringDeserializer;
import com.superyuuki.yuukonfig.inbuilt.text.StringSerializer;
import com.superyuuki.yuukonfig.inbuilt.text.UUIDDeserializer;
import com.superyuuki.yuukonfig.inbuilt.text.UUIDSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        serializers.add(new SectionSerializer());
        deserializers.add(new SectionDeserializer());

        //primitives
        serializers.add(new BooleanSerializer());
        serializers.add(new IntegerSerializer());
        serializers.add(new DoubleSerializer());
        serializers.add(new StringSerializer());

        deserializers.add(new TypedDeserializer.Mock<>(Boolean.class, new BooleanDeserializer()));
        deserializers.add(new TypedDeserializer.Mock<>(Integer.class, new IntegerDeserializer()));
        deserializers.add(new TypedDeserializer.Mock<>(Double.class, new DoubleDeserializer()));
        deserializers.add(new TypedDeserializer.Mock<>(String.class, new StringDeserializer()));

        //enum
        serializers.add(new EnumSerializer());
        deserializers.add(new EnumDeserializer());

        //collections
        serializers.add(new ListSerializer());
        deserializers.add(new ListDeserializer());

        //uuid
        serializers.add(new UUIDSerializer());
        deserializers.add(new TypedDeserializer.Mock<>(UUID.class, new UUIDDeserializer()));

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
