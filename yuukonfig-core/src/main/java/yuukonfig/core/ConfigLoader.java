package yuukonfig.core;

import yuukonfig.core.err.YuuKonfigException;
import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.node.Mapping;
import yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonstants.GenericPath;

import java.nio.file.Path;

public class ConfigLoader<T>  {

    final RawNodeFactory factory;
    final BaseManipulation manipulation;
    final Class<T> configClazz;
    final Path configPath;

    public ConfigLoader(RawNodeFactory factory, BaseManipulation manipulation, Class<T> configClazz, Path configPath) {
        this.factory = factory;
        this.manipulation = manipulation;
        this.configClazz = configClazz;
        this.configPath = configPath;
    }

    public class ContainsContent {
        public final Mapping mapping;

        public ContainsContent(Mapping mapping) {
            this.mapping = mapping;
        }

        public T loadToMemoryConfig() {
            return manipulation.safeDeserialize(mapping, configClazz);
        }

        public ContainsContent overrideMainConfigFromFile(Path path) {
            Mapping mappingFromOverrideFile = factory.loadFromFile(path);
            Mapping newlyCombinedContent = factory.mergeMappingsOrdered(mappingFromOverrideFile, mapping);

            return new ContainsContent(newlyCombinedContent);
        }

        public ContainsContent writeToFile() {
            factory.writeToFile(mapping, configPath);

            return this;
        }

    }

    public ContainsContent loadString(String contents) throws YuuKonfigException {
        Mapping mp = factory.loadString(contents);

        return new ContainsContent(mp);
    }

    public ContainsContent load() throws YuuKonfigException {
        Mapping userContent = factory.loadFromFile(configPath); //loadfrom
        Mapping defaultContent = manipulation.serializeDefaultCtx(configClazz, new GenericPath()).asMapping();
        Mapping combinedContent = factory.mergeMappings(userContent, defaultContent);

        return new ContainsContent(
                combinedContent
        );
    }

    public ContainsContent loadOnlyDefaults() throws YuuKonfigException {
        Mapping defaultContent = manipulation.serializeDefaultCtx(configClazz, new GenericPath()).asMapping();

        return new ContainsContent(
                defaultContent
        );
    }

    public ContainsContent loadOnlyUser() throws YuuKonfigException {
        Mapping userContent = factory.loadFromFile(configPath); //loadfrom

        return new ContainsContent(userContent);
    }

}
