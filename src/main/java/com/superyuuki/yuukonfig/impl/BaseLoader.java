package com.superyuuki.yuukonfig.impl;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlPrinter;
import com.amihaiemil.eoyaml.extensions.MergedYamlMapping;
import com.superyuuki.yuukonfig.BadConfigException;
import com.superyuuki.yuukonfig.loader.ConfigLoader;
import com.superyuuki.yuukonfig.manipulation.Manipulation;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class BaseLoader<T> implements ConfigLoader<T> {

    private final Manipulation manipulation;
    private final Class<T> configClazz;
    private final Path configPath;

    public BaseLoader(Manipulation manipulation, Class<T> configClazz, Path configPath) {
        this.manipulation = manipulation;
        this.configClazz = configClazz;
        this.configPath = configPath;
    }

    @Override
    public T load() throws BadConfigException {
        //read defaults and value, combine

        YamlMapping userContent = loadFrom();
        YamlMapping defaultContent = manipulation.serializeDefault(configClazz, new String[]{}).asMapping();
        YamlMapping combinedContent = new MergedYamlMapping(userContent, defaultContent, false);

        Object config = manipulation.deserialize(combinedContent, "(n/a)", configClazz);

        //export defaults since they have passed validation!

        try {
            YamlPrinter printer = Yaml.createYamlPrinter(
                    new FileWriter(configPath.toFile())
            );

            printer.print(combinedContent); //file map.yml will be created and written.
        } catch (IOException ioException) {
            throw new BadConfigException(
                    String.format("Something went wrong loading a config titled: %s, an IO exception: %s: was thrown.", configPath, ioException)
            );
        }

        return configClazz.cast(config);
    }

    @Override
    public T loadWithoutDefaults() throws BadConfigException {
        return configClazz.cast(
                manipulation.deserialize(
                        loadFrom(),
                        "(n/a)",
                        configClazz
                )
        );
    }

    YamlMapping loadFrom() throws BadConfigException {
        try {
            return Yaml.createYamlInput(configPath.toFile()).readYamlMapping(); //read map
        } catch (FileNotFoundException e) {
            return Yaml.createYamlMappingBuilder().build(); //empty map
        } catch (IOException e) {
            throw new BadConfigException(
                    String.format("Something went wrong loading a config titled: %s, an IO exception: %s: was thrown.", configPath, e)
            );
        }
    }
}
