package com.superyuuki.yuukonfig.impl.load;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlPrinter;
import com.amihaiemil.eoyaml.extensions.MergedYamlMapping;
import com.superyuuki.yuukonfig.CommonRegistry;
import com.superyuuki.yuukonfig.ConfigLoader;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.decompose.Deserializers;
import com.superyuuki.yuukonfig.impl.request.RequestImpl;
import com.superyuuki.yuukonfig.impl.request.UserRequestImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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

    public static <C> BaseLoader<C> defaults(File file, Class<C> configType) {
        CommonRegistry registry = BaseRegistry.defaults();

        return new BaseLoader<>(file, configType, registry.makeSerializers(), registry.makeDeserializers());
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
        YamlMapping defaults = registry.serializeDefault(new RequestImpl(configType)).asMapping();
        YamlMapping combined = new MergedYamlMapping(userContent, defaults, false);

        //read defaults

        C config = deserializers.deserializeTyped(
                combined,
                new UserRequestImpl<>(configType),
                file.getName()
        );

        //export defaults since they have passed validation!

        try {
            YamlPrinter printer = Yaml.createYamlPrinter(
                    new FileWriter(file)
            );

            printer.print(combined); //file map.yml will be created and written.
        } catch (IOException ioException) {
            throw new ConfigIOFailure(file.getName(), ioException);
        }

        return config;
    }
}
