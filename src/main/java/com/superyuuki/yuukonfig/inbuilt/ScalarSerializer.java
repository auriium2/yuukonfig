package com.superyuuki.yuukonfig.inbuilt;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.compose.Serializer;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.request.Request;

public interface ScalarSerializer extends Serializer {

    @Override
    default YamlNode serializeObject(Request rq, Object object, Serializers serializers) {
        return Yaml.createYamlScalarBuilder().addLine(object.toString()).buildPlainScalar();
    }
}
