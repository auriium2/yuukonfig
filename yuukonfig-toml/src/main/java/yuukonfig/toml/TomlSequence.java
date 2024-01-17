package yuukonfig.toml;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.err.Exceptions;
import yuukonfig.core.node.*;
import xyz.auriium.yuukonstants.GenericPath;

import java.util.Iterator;
import java.util.List;

public class TomlSequence implements Sequence {

    final List<Node> shit;
    final GenericPath path;

    public TomlSequence(List<Node> shit, GenericPath path) {
        this.shit = shit;
        this.path = path;
    }

    void checkValue(int i) {
        if (i > shit.size() - 1) throw Exceptions.SEQUENCE_MISSING(path, i + 1, shit.size());
    }

    @Override
    public Node atIndexPossiblyEmpty(int i) {
        Node possible = shit.get(i);

        if (possible == null) possible = new NotPresentNode(path.append("<" + i + ">"));
        return possible;
    }

    @Override
    public Node atIndexGuaranteed(int i) {
        Node possible = atIndexPossiblyEmpty(i);
        if (possible.type() == Type.NOT_PRESENT) throw Exceptions.UNEXPECTED_EMPTY_NODE(path.append("<" + i + ">"));

        return possible;
    }

    @Override
    public List<Node> getList() {
        return shit;
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
        throw Exceptions.UNEXPECTED_NODE_TYPE(path, Type.SCALAR, Type.SEQUENCE);
    }

    @Override
    public Mapping asMapping() throws BadValueException, ClassCastException {
        throw Exceptions.UNEXPECTED_NODE_TYPE(path, Type.MAPPING, Type.SEQUENCE);
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

    @Override
    public GenericPath path() {
        return path;
    }
}
