package xyz.auriium.yuukonfig.toml;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import xyz.auriium.yuukonfig.core.err.BadValueException;
import xyz.auriium.yuukonfig.core.node.Mapping;
import xyz.auriium.yuukonfig.core.node.Node;
import xyz.auriium.yuukonfig.core.node.Scalar;
import xyz.auriium.yuukonfig.core.node.Sequence;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TomlSequence implements Sequence {

    final List<Node> shit;


    public TomlSequence(List<Node> shit) {
        this.shit = shit;
    }

    @Override
    public Node atIndex(int i) {
        return shit.get(i);
    }

    @Override
    public Iterator<Node> iterator() {
        return shit.iterator();
    }

    @Override
    public boolean isEmpty() {
        return shit.isEmpty();
    }

    @Override
    public Type type() {
        return Type.SEQUENCE;
    }

    @Override
    public Scalar asScalar() throws BadValueException, ClassCastException {
        throw new ClassCastException("bad");
    }

    @Override
    public Mapping asMapping() throws BadValueException, ClassCastException {
        throw new ClassCastException("bad");
    }

    @Override
    public Sequence asSequence() throws BadValueException, ClassCastException {
        return this;
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws ClassCastException {
        if (clazz == List.class) {
            return (T) shit;
        }

        throw new ClassCastException("is: a toml");
    }
}
