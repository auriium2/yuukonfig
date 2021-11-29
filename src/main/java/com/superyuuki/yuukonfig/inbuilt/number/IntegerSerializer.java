package com.superyuuki.yuukonfig.inbuilt.number;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.serialize.compose.Serializers;
import com.superyuuki.yuukonfig.serialize.compose.TypedSerializer;

public class IntegerSerializer implements TypedSerializer<Integer> {
    @Override
    public YamlNode serializeDefault(Class<? extends Integer> request, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine("0").buildPlainScalar("Default value - please change!");
    }

    @Override
    public YamlNode serializeObject(Class<? extends Integer> request, Integer object, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine(object.toString()).buildPlainScalar();
    }
}
