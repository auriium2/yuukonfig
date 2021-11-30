package com.superyuuki.yuukonfig.decompose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.impl.decompose.ParsingFailure;
import com.superyuuki.yuukonfig.request.Request;

public interface TypedDeserializer<T> {

    T deserialize(YamlNode node, Request rq, DeserializerContext ctx) throws ParsingFailure;

    class Mock<T> implements Deserializer {

        private final Class<T> target;
        private final TypedDeserializer<T> deserializer;

        public Mock(Class<T> target, TypedDeserializer<T> deserializer) {
            this.target = target;
            this.deserializer = deserializer;
        }

        @Override
        public int handles(Class<?> clazz) {
            if (clazz.equals(target)) return Priority.IMMEDIATE_HANDLE;

            if (target.isAssignableFrom(clazz)) return Priority.PRIORITY_HANDLE;

            return Priority.DONT_HANDLE;
        }

        @Override
        public Object deserialize(YamlNode node, Request rq, DeserializerContext ctx) {
            Class<?> requestedClass = rq.requestedClass();
            //should be safe
            if (target.isAssignableFrom(requestedClass)) return this.deserializer.deserialize(node, rq, ctx);

            throw new ClassCastException("Unexpected class cast received when deserializing a config node!");
        }
    }
}
