package com.superyuuki.yuukonfig.compose;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;

public class IntegerSerializer implements TypedSerializer<Integer> {
    @Override
    public YamlNode serializeDefault(Class<? extends Integer> request, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().buildPlainScalar("0");
    }

    @Override
    public YamlNode serializeObject(Class<? extends Integer> request, Integer object, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().buildPlainScalar(object.toString());
    }
}
