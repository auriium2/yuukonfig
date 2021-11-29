package com.superyuuki.yuukonfig.compose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;

public interface TypedSerializer<T> {

    YamlNode serializeDefault(Class<? extends T> request, Serializers serializers);
    YamlNode serializeObject(Class<? extends T> request, T object, Serializers serializers);

    class Mock<T> implements Serializer {

        private final Class<T> target;
        private final TypedSerializer<T> typedSerializer;

        public Mock(Class<T> target, TypedSerializer<T> typedSerializer) {
            this.target = target;
            this.typedSerializer = typedSerializer;
        }


        @Override
        public int handles(Class<?> clazz) {
            if (clazz.equals(target)) return Priority.IMMEDIATE_HANDLE;

            if (target.isAssignableFrom(clazz)) return Priority.PRIORITY_HANDLE;

            return Priority.DONT_HANDLE;
        }

        @SuppressWarnings("unchecked")
        @Override
        public YamlNode serializeDefault(Class<?> request, Serializers serializers) {
            if (target.isAssignableFrom(request)) return typedSerializer.serializeDefault((Class<? extends T>) request, serializers);

            throw new ClassCastException("Unexpected class cast received when serializing a config node!");
        }

        @SuppressWarnings("unchecked")
        @Override
        public YamlNode serializeObject(Class<?> request, Object object, Serializers serializers) {
            if (target.isAssignableFrom(request)) return typedSerializer.serializeObject((Class<? extends T>) request, target.cast(request), serializers);

            throw new ClassCastException("Unexpected class cast received when serializing a config node!");
        }
    }

}
