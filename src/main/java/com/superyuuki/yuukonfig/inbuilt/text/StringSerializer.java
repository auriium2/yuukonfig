package com.superyuuki.yuukonfig.inbuilt.text;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.serialize.compose.Serializers;
import com.superyuuki.yuukonfig.serialize.compose.TypedSerializer;

public class StringSerializer implements TypedSerializer<String> {

    @Override
    public YamlNode serializeDefault(Class<? extends String> request, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine("").buildPlainScalar("Default value - please change!"); //TODO customizable empty text
    }

    @Override
    public YamlNode serializeObject(Class<? extends String> request, String object, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine(object).buildPlainScalar();
    }
}