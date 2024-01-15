package yuukonfig.core.impl;

import yuukonfig.core.err.BadConfigException;
import yuukonfig.core.ConfigLoader;
import yuukonfig.core.manipulation.Manipulation;
import yuukonfig.core.node.Mapping;
import yuukonfig.core.node.RawNodeFactory;
import yuukonstants.GenericPath;

import java.nio.file.Path;
import java.util.Optional;

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



        Object config = manipulation.deserialize(combinedContent, new GenericPath(new String[] {manipulation.configName()}), configClazz);

        factory.writeToFile(combinedContent, configPath);


        return configClazz.cast(config);
    }

    @Override
    public T loadWithoutDefaults() throws BadConfigException {
        return configClazz.cast(
                manipulation.deserialize(
                        factory.loadFromFile(configPath), //loadfrom
                        new GenericPath(new String[] {manipulation.configName()}),
                        configClazz
                )
        );
    }

    @Override
    public T onlyDefaults() throws BadConfigException {
        Mapping defaultContent = manipulation.serializeDefault(configClazz, new String[]{}).asMapping();

        Object config = manipulation.deserialize(defaultContent, new GenericPath(new String[] {manipulation.configName()}), configClazz);

        factory.writeToFile(defaultContent, configPath);
        return configClazz.cast(config);
    }

    @Override
    public Optional<BadConfigException> issues() {
        return Optional.empty(); //TODO implement this
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
