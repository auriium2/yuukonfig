package com.superyuuki.yuukonfig;

import com.amihaiemil.eoyaml.YamlNode;

public interface ConfigLoader<C> {

    C load();

}
