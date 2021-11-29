package com.superyuuki.yuukonfig.decompose;

import com.amihaiemil.eoyaml.YamlNode;

public class IntegerDeserializer implements TypedDeserializer<Integer> {

    @Override
    public Integer deserialize(Class<? extends Integer> clazz, YamlNode node, Deserializers deserializer) {

        return Integer.parseInt(node.asScalar().value());

    }
}
