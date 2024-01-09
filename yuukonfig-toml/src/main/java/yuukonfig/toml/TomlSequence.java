package yuukonfig.toml;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.*;

import java.util.Iterator;
import java.util.List;

public class TomlSequence implements Sequence {

    final List<Node> shit;


    public TomlSequence(List<Node> shit) {
        this.shit = shit;
    }

    @Override
    public Node atIndex(int i) {
        Node possible =  shit.get(i);

        if (possible == null) possible = new RawNodeFactory.NotPresentNode();
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
