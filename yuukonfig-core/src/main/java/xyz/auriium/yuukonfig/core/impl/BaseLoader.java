package xyz.auriium.yuukonfig.core.impl;

import xyz.auriium.yuukonfig.core.err.BadConfigException;
import xyz.auriium.yuukonfig.core.ConfigLoader;
import xyz.auriium.yuukonfig.core.manipulation.Manipulation;
import xyz.auriium.yuukonfig.core.node.Mapping;
import xyz.auriium.yuukonfig.core.node.RawNodeFactory;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class BaseLoader<T> implements ConfigLoader<T> {

    final RawNodeFactory factory;
    final Manipulation manipulation;
    final Class<T> configClazz;
    final Path configPath;

    public BaseLoader(RawNodeFactory factory, Manipulation manipulation, Class<T> configClazz, Path configPath) {
        this.factory = factory;
        this.manipulation = manipulation;
        this.configClazz = configClazz;
        this.configPath = configPath;
    }

    @Override
    public T load() throws BadConfigException {
        //read defaults and value, combine

        Mapping userContent = factory.loadFromFile(configPath); //loadfrom
        Mapping defaultContent = manipulation.serializeDefault(configClazz, new String[]{}).asMapping();
        Mapping combinedContent = factory.mergeMappings(userContent, defaultContent);

        Object config = manipulation.deserialize(combinedContent, "(n/a)", configClazz);

        //export defaults since they have passed validation!

        /**
         * YamlPrinter printer = Yaml.createYamlPrinter(
         *                     new FileWriter(configPath.toFile())
         *             );
         *
         *             printer.print(combinedContent); //file map.yml will be created and written.
         */

        factory.writeToFile(combinedContent, configPath);


        return configClazz.cast(config);
    }

    @Override
    public T loadWithoutDefaults() throws BadConfigException {
        return configClazz.cast(
                manipulation.deserialize(
                        factory.loadFromFile(configPath), //loadfrom
                        "(n/a)",
                        configClazz
                )
        );
    }

    @Override
    public T onlyDefaults() throws BadConfigException {
        Mapping defaultContent = manipulation.serializeDefault(configClazz, new String[]{}).asMapping();

        Object config = manipulation.deserialize(defaultContent, "(n/a)", configClazz);

        factory.writeToFile(defaultContent, configPath);
        return configClazz.cast(config);
    }

  /*  Mapping loadFrom() throws BadConfigException {
        try {
            return

            return Yaml.createYamlInput(configPath.toFile()).readYamlMapping(); //read map
        } catch (FileNotFoundException e) {
            return Yaml.createYamlMappingBuilder().build(); //empty map
        } catch (IOException e) {
            throw new BadConfigException(
                    String.format("Something went wrong loading a config titled: %s, an IO exception: %s: was thrown.", configPath, e)
            );
        }
    }*/
}
