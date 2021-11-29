package com.superyuuki.yuukonfig.inbuilt.collection;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.serialize.compose.Serializers;
import com.superyuuki.yuukonfig.serialize.compose.TypedSerializer;
import net.jodah.typetools.TypeResolver;

import java.util.List;

public class ListSerializer implements TypedSerializer<List> {


    @Override
    public YamlNode serializeDefault(Class<? extends List> request, Serializers serializers) {

        Class<?> sub = TypeResolver.resolveRawArguments(List.class, request)[0];

        YamlNode defaultChild = serializers.serializeDefault(sub);

        return Yaml.createYamlSequenceBuilder().add(defaultChild).build();
    }

    @Override
    public YamlNode serializeObject(Class<? extends List> request, List object, Serializers serializers) {

        Class<?> sub = TypeResolver.resolveRawArguments(List.class, request)[0];

        throw new IllegalStateException(String.valueOf(sub));

        //YamlSequenceBuilder builder = Yaml.createYamlSequenceBuilder();

        /*for (Object act : object) {
            if (sub.isInstance(act)) throw new IllegalStateException("act: " + act.getClass() + " sub: " + sub);

            builder = builder.add(serializers.serialize(object));
        }

        return builder.build();*/
    }
}
