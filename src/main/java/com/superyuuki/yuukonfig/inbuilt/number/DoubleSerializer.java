package com.superyuuki.yuukonfig.inbuilt.number;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.compose.TypedSerializer;

public class DoubleSerializer implements TypedSerializer<Double> {
    @Override
    public YamlNode serializeDefault(Class<? extends Double> request, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine("0.0").buildPlainScalar("Default value - please change!");
    }

    @Override
    public YamlNode serializeObject(Class<? extends Double> request, Double object, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine("0.0").buildPlainScalar();
    }
}
