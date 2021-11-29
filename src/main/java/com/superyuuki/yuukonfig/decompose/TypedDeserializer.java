package com.superyuuki.yuukonfig.decompose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;

public interface TypedDeserializer<T> {

    T deserialize(Class<? extends T> clazz, YamlNode node, Deserializers deserializer);

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

        @SuppressWarnings("unchecked")
        @Override
        public Object deserialize(Class<?> requestedClass, YamlNode node, Deserializers deserializers) {

            //should be safe
            if (target.isAssignableFrom(requestedClass)) return this.deserializer.deserialize((Class<? extends T>) requestedClass, node, deserializers);

            throw new ClassCastException("Unexpected class cast received when deserializing a config node!");
        }
    }
}
