package yuukonfig.toml;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.err.Exceptions;
import yuukonfig.core.node.*;
import xyz.auriium.yuukonstants.GenericPath;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class TomlMapping implements Mapping {

    final Map<String,Node> map;
    final GenericPath path;

    public TomlMapping(Map<String,Node> map, GenericPath path) {
        this.map = map;
        this.path = path;
    }

    void checkIfPresent(String key) {
        if (map.get(key) == null) {
            throw Exceptions.UNEXPECTED_EMPTY_NODE(path.append(key));
        }

    }

    @Override
    public Mapping yamlMapping(String key) {
        checkIfPresent(key);

        return map.get(key).asMapping();
    }

    @Override
    public Sequence yamlSequence(String key) {
        checkIfPresent(key);

        return map.get(key).asSequence();
    }

    @Override
    public String string(String key) {
        checkIfPresent(key);

        return map.get(key).asScalar().value();
    }

    @Override
    public String foldedBlockScalar(String key) {
        checkIfPresent(key);

        return map.get(key).asScalar().value();
    }


    @Override
    public Node valuePossiblyMissing(String key) {
        var possiblyNull = map.get(key);
        if (possiblyNull == null) return new NotPresentNode(path.append(key));

        return map.get(key);
    }

    @Override
    public Node valueGuaranteed(String key) throws BadValueException {
        checkIfPresent(key);
        var nodeMaybe = map.get(key);
        if (nodeMaybe.type() == Type.NOT_PRESENT) throw Exceptions.UNEXPECTED_EMPTY_NODE(path.append(key));

        return map.get(key);
    }

    @Override
    public int integer(String key) {
        checkIfPresent(key);

        return Integer.parseInt(map.get(key).asScalar().value());
    }

    @Override
    public float floatNumber(String key) {
        checkIfPresent(key);

        return Float.parseFloat(map.get(key).asScalar().value());
    }

    @Override
    public double doubleNumber(String key) {
        checkIfPresent(key);

        return Double.parseDouble(map.get(key).asScalar().value());
    }

    @Override
    public long longNumber(String key) {
        checkIfPresent(key);

        return Long.parseLong(map.get(key).asScalar().value());
    }

    @Override
    public LocalDate date(String key) {
        checkIfPresent(key);

        return LocalDate.parse(map.get(key).asScalar().value());
    }

    @Override
    public LocalDateTime dateTime(String key) {
        checkIfPresent(key);

        return LocalDateTime.parse(map.get(key).asScalar().value());
    }

    @Override
    public Map<String, Node> getMap() {
        return map;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Type type() {
        return Type.MAPPING;
    }

    @Override
    public Scalar asScalar() throws BadValueException, ClassCastException {
        throw new ClassCastException("not a scalar");
    }

    @Override
    public Mapping asMapping() throws BadValueException, ClassCastException {
        return this;
    }

    @Override
    public Sequence asSequence() throws BadValueException, ClassCastException {
        throw new ClassCastException("not a sequence");
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws ClassCastException {
        if (clazz == Map.class) {
            return (T) map;
        }

        throw new ClassCastException("is: a toml");
    }

    @Override
    public GenericPath path() {
        return path;
    }


}
