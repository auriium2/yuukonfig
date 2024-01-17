package yuukonfig.core.node;

import yuukonfig.core.SerializeFunction;
import yuukonfig.core.err.BadValueException;
import yuukonfig.core.err.Exceptions;
import xyz.auriium.yuukonstants.GenericPath;
import yuukonfig.core.manipulation.Contextual;

import java.lang.reflect.Type;
import java.nio.file.Path;

/**
 * This exists because Yuukonfig wasnt designed around multiple types of configs, but i needed that functionality
 * So here is a class with a bunch of random data in it
 */
public interface RawNodeFactory {

    interface SequenceBuilder {
        void add(Node node);

        void addSerialize(SerializeFunction sz, Object data, Class<?> toSerializeAs);
        void addSerialize(SerializeFunction sz, Object data, Class<?> toSerializeAs, Contextual<Type> ctx);

        Sequence build(String... aboveComment);
    }

    interface MappingBuilder {
        void add(String key, Node node);
        Mapping build(String... aboveComment);
    }

    SequenceBuilder makeSequenceBuilder(GenericPath path);
    MappingBuilder makeMappingBuilder(GenericPath path);

    Node scalarOf(GenericPath path, Object data);

    Mapping loadString(String simulatedConfigInStringForm);
    Mapping loadFromFile(Path path);

    Mapping mergeMappings(Mapping preserved, Mapping optimisticallyADded);
    Mapping mergeMappingsOrdered(Mapping dominant, Mapping recessive);

    void writeToFile(Mapping toWrite, Path location);

    default Node notPresentOf(GenericPath path) {
        return new NotPresentNode(path);
    }

}
