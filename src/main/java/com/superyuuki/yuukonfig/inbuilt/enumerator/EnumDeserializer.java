package com.superyuuki.yuukonfig.inbuilt.enumerator;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.serialize.decompose.Deserializer;
import com.superyuuki.yuukonfig.serialize.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.serialize.RequestContext;
import com.superyuuki.yuukonfig.error.UnexpectedRequestFailure;
import com.superyuuki.yuukonfig.error.parsing.ParsingFailure;

import java.util.Arrays;

public class EnumDeserializer implements Deserializer {
    @Override
    public int handles(Class<?> clazz) {
        if (clazz.isEnum()) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object deserialize(YamlNode node, RequestContext<?> rq, DeserializerContext ctx) throws ParsingFailure {

        Class<?> rawClass = rq.requestedClass();
        if (!rawClass.isEnum()) throw new UnexpectedRequestFailure(rawClass, Enum.class);

        String raw = node.asScalar().value();
        Class<Enum<?>> enumClass = (Class<Enum<?>>) rawClass;

        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            String name = enumConstant.name();
            boolean equal = name.equalsIgnoreCase(raw);
            if (equal) {
                return enumConstant;
            }
        }
        throw new ParsingFailure(rq, ctx, String.format("%s is not a valid enum! Supported enums are: %s", raw, Arrays.toString(enumClass.getEnumConstants())));

    }


}
