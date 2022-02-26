package com.superyuuki.yuukonfig.manipulation;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.BadValueException;

public interface Manipulator {

    int handles();

    Object deserialize(YamlNode node, String exceptionalKey) throws BadValueException;

    YamlNode serializeObject(Object object, String[] comment);
    YamlNode serializeDefault(String[] comment);

}
