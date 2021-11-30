package com.superyuuki.yuukonfig.inbuilt.collection;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.decompose.Deserializer;
import com.superyuuki.yuukonfig.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.impl.decompose.ParsingFailure;
import com.superyuuki.yuukonfig.impl.request.RequestImpl;
import com.superyuuki.yuukonfig.request.Request;

import java.util.ArrayList;
import java.util.List;

public class ListDeserializer implements Deserializer {
    @Override
    public int handles(Class<?> clazz) {
        if (clazz.equals(List.class)) {
            return Priority.HANDLE;
        }

        return Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(YamlNode node, Request rq, DeserializerContext ctx) throws ParsingFailure {

        String displayKey = rq.keyDisplayName().orElse("(developer error - no keypath found)");

        Class<?> parseAs = Collects.getGenericType(
                rq,
                displayKey
        );

        List<Object> uncheckedList = new ArrayList<>();

        for (YamlNode child : node.asSequence()) {
            uncheckedList.add(
                    ctx.deserializers().deserialize(
                            child,
                            new RequestImpl(
                                    parseAs,
                                    displayKey //self
                            ),
                            ctx
                    )
            );
        }

        return List.copyOf(uncheckedList);
    }
}
