package xyz.auriium.yuukonfig.core.node;

import java.nio.file.Path;

/**
 * This exists because Yuukonfig wasnt designed around multiple types of configs, but i needed that functionality
 * So here is a class with a bunch of random data in it
 */
public interface RawNodeFactory {

    interface SequenceBuilder {
        void add(Node node);

        Sequence build(String... aboveComment);
    }

    interface MappingBuilder {
        void add(String key, Node node);

        Mapping build(String... aboveComment);
    }

    SequenceBuilder makeSequenceBuilder();
    MappingBuilder makeMappingBuilder();

    Node scalarOf(Object data, String inlineComment, String... aboveComment);
    Node scalarOf(Object data, String... aboveComment);

    Mapping loadString(String simulatedConfigInStringForm);
    Mapping loadFromFile(Path path);

    Mapping mergeMappings(Mapping one, Mapping two);

    void writeToFile(Mapping toWrite, Path location);
}
