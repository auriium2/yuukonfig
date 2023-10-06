package yuukonfig.toml;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.node.Mapping;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.Scalar;
import yuukonfig.core.node.Sequence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class TomlMapping implements Mapping {

    final Map<String,Node> map;

    public TomlMapping(Map<String,Node> map) {
        this.map = map;
    }

    @Override
    public Mapping yamlMapping(String key) {
        Node output = map.get(key);
        if (output.type() != Type.MAPPING) {
            throw new IllegalStateException("not a map!");
        }

        return output.asMapping();
    }

    @Override
    public Sequence yamlSequence(String key) {
        Node output = map.get(key);
        if (output.type() != Type.SEQUENCE) {
            throw new IllegalStateException("not a map!");
        }


        return output.asSequence();
    }

    @Override
    public String string(String key) {
        return map.get(key).asScalar().value();
    }

    @Override
    public String foldedBlockScalar(String key) {
        return map.get(key).asScalar().value();
    }


    @Override
    public Node value(String key) {
        return map.get(key);
    }

    @Override
    public int integer(String key) {
        return Integer.parseInt(map.get(key).asScalar().value());
    }

    @Override
    public float floatNumber(String key) {
        return Float.parseFloat(map.get(key).asScalar().value());
    }

    @Override
    public double doubleNumber(String key) {
        return Double.parseDouble(map.get(key).asScalar().value());
    }

    @Override
    public long longNumber(String key) {
        return Long.parseLong(map.get(key).asScalar().value());
    }

    @Override
    public LocalDate date(String key) {
        return LocalDate.parse(map.get(key).asScalar().value());
    }

    @Override
    public LocalDateTime dateTime(String key) {
        return LocalDateTime.parse(map.get(key).asScalar().value());
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
}
