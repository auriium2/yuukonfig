package com.superyuuki.yuukonfig.compose;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.request.Request;

public interface TypedSerializer<T> {

    YamlNode serializeDefault(Request request, Serializers serializers);
    YamlNode serializeObject(Request request, T object, Serializers serializers);

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

        @Override
        public YamlNode serializeDefault(Request request, Serializers serializers) {
            return typedSerializer.serializeDefault(request, serializers);
        }

        @Override
        public YamlNode serializeObject(Request request, Object object, Serializers serializers) {
            return typedSerializer.serializeObject(request, target.cast(object), serializers);
        }
    }

}
