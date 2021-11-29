package com.superyuuki.yuukonfig.inbuilt.bool;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.compose.TypedSerializer;

public class BooleanSerializer implements TypedSerializer<java.lang.Boolean> {
    @Override
    public YamlNode serializeDefault(Class<? extends Boolean> request, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine("false").buildPlainScalar("Default value - please change!"); //TODO defaults
    }

    @Override
    public YamlNode serializeObject(Class<? extends Boolean> request, Boolean object, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine(object.toString()).buildPlainScalar();
    }
}
