package com.superyuuki.yuukonfig.config;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.extensions.MergedYamlMapping;
import com.superyuuki.yuukonfig.ConfigLoader;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.decompose.Deserializers;
import com.superyuuki.yuukonfig.error.ConfigIOFailure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BaseLoader<C> implements ConfigLoader<C> {

    private final File file;
    private final Class<C> configType;
    private final Serializers registry;
    private final Deserializers deserializers;

    public BaseLoader(File file, Class<C> configType, Serializers registry, Deserializers deserializers) {
        this.file = file;
        this.configType = configType;
        this.registry = registry;
        this.deserializers = deserializers;
    }

    @Override
    public C load() {
        YamlMapping userContent;

        try {
            userContent = Yaml.createYamlInput(file).readYamlMapping(); //read map
        } catch (FileNotFoundException e) {
            userContent = Yaml.createYamlMappingBuilder().build(); //empty map
        } catch (IOException e) {
            throw new ConfigIOFailure(file.getName(), e);
        }

        //load defaults
        YamlMapping defaults = registry.serialize(configType).asMapping();
        YamlMapping combined = new MergedYamlMapping(userContent, defaults, false);

        //read defaults

        C config = deserializers.deserialize(combined, configType);

        //export defaults


        return null;
    }
}
