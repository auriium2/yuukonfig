package com.superyuuki.yuukonfig.serialize.decompose;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlNode;
import com.amihaiemil.eoyaml.YamlNodeNotFoundException;
import com.superyuuki.yuukonfig.Priority;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.annotate.ConfKey;
import com.superyuuki.yuukonfig.error.TooManyArgsFailure;
import com.superyuuki.yuukonfig.serialize.RequestContext;
import com.superyuuki.yuukonfig.serialize.BaseRequestCtx;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class InterfaceDeserializer implements Deserializer {

    @Override
    public int handles(Class<?> clazz) {
        if (Section.class.isAssignableFrom(clazz)) return Priority.HANDLE;

        return Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(YamlNode node, RequestContext<?> rq, DeserializerContext ctx) {
        Class<?> req = rq.requestedClass();

        YamlMapping mapping = node.asMapping();

        Map<String, Object> backingMap = new HashMap<>();

        for (Method method : req.getMethods()) {
            if (method.getParameterCount() != 0) throw new TooManyArgsFailure(method.getName());

            String key = getKey(method);
            YamlNode nullable = mapping.value(key);

            if (nullable == null) throw new YamlNodeNotFoundException("No YAML found for key: " + key);

            Class<?> returnType = method.getReturnType();

            backingMap.put(method.getName(), ctx.deserializers().deserialize(
                    nullable,
                    new BaseRequestCtx<>(returnType, method.getGenericReturnType(), key),
                    ctx
            ));
        }

        return req.cast(Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{ req },
                new MappedInvocationHandler(Map.copyOf(backingMap))
        ));
    }

    String getKey(Method method) {
        if (method.isAnnotationPresent(ConfKey.class)) {
            return method.getAnnotation(ConfKey.class).value();
        }

        return method.getName();
    }
}
