package yuukonfig.yaml;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.amihaiemil.eoyaml.YamlSequence;
import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.Mapping;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.Scalar;
import yuukonfig.core.node.Sequence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class YamlSequenceShiv implements Sequence {

    final YamlSequence sequence;

    public YamlSequenceShiv(YamlSequence sequence) {
        this.sequence = sequence;
    }

    static final Function<com.amihaiemil.eoyaml.YamlNode, Node> CONVERT = i -> {

        if (i == null) return null;


        if (i.type() == com.amihaiemil.eoyaml.Node.SEQUENCE) {
            return new YamlSequenceShiv(i.asSequence());
        }
        if (i.type() == com.amihaiemil.eoyaml.Node.MAPPING) {
            return new YamlMappingShiv(i.asMapping());
        }
        if (i.type() == com.amihaiemil.eoyaml.Node.SCALAR) {
            return new YamlScalarShiv(i.asScalar());
        }
        if (i.type() == com.amihaiemil.eoyaml.Node.STREAM) {
            return new YamlSequenceShiv(i.asSequence());
        }

        return new YamlScalarShiv(Yaml.createYamlScalarBuilder().buildPlainScalar());
    };

    @Override
    public Iterator<Node> iterator() {
        return sequence.values().stream().map(CONVERT).iterator();
    }

    @Override
    public boolean isEmpty() {
        return sequence.isEmpty();
    }

    @Override
    public Type type() {
        return Type.SEQUENCE;
    }

    @Override
    public Scalar asScalar() throws BadValueException, ClassCastException {
        throw new ClassCastException("this is a sequence");
    }

    @Override
    public Mapping asMapping() throws BadValueException, ClassCastException {
        throw new ClassCastException("this is a sequence");
    }

    @Override
    public Sequence asSequence() throws BadValueException, ClassCastException {
        return this;
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws ClassCastException {
        if (clazz == YamlNode.class) {
            return clazz.cast(sequence);
        }

        throw new ClassCastException("invalid access of unexpected type");

    }

    @Override
    public Node atIndex(int index) {
        YamlNode searched = null;
        int count = 0;
        for (final YamlNode node : sequence.values()) {
            if (count == index) {
                searched = node;
            }
            count = count + 1;
        }
        return CONVERT.apply(searched);
    }

    @Override
    public List<Node> getList() {
        List<Node> list = new ArrayList<>();

        for (YamlNode node : sequence) {
            list.add(YamlSequenceShiv.CONVERT.apply(node));
        }

        return list;
    }
}
