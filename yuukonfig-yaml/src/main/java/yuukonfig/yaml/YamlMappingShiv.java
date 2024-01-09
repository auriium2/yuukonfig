package yuukonfig.yaml;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlNode;
import yuukonfig.core.err.BadValueException;
import yuukonfig.core.err.Exceptions;
import yuukonfig.core.node.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class YamlMappingShiv implements Mapping {

    final YamlMapping yamlMapping;

    public YamlMappingShiv(YamlMapping yamlMapping) {
        this.yamlMapping = yamlMapping;
    }

    @Override
    public Mapping yamlMapping(String key) {
        return new YamlMappingShiv(yamlMapping.yamlMapping(key));
    }

    @Override
    public Sequence yamlSequence(String key) {
        return new YamlSequenceShiv(yamlMapping.yamlSequence(key));
    }

    @Override
    public String string(String key) {
        return yamlMapping.string(key);
    }

    @Override
    public String foldedBlockScalar(String key) {
        return yamlMapping.foldedBlockScalar(key);
    }

    @Override
    public Node value(String key) {

        YamlNode y = yamlMapping.value(key);

        return YamlSequenceShiv.CONVERT.apply(y);
    }

    @Override
    public int integer(String key) {
        return yamlMapping.integer(key);
    }

    @Override
    public float floatNumber(String key) {
        return yamlMapping.floatNumber(key);
    }

    @Override
    public double doubleNumber(String key) {
        return yamlMapping.doubleNumber(key);
    }

    @Override
    public long longNumber(String key) {
        return yamlMapping.longNumber(key);
    }

    @Override
    public LocalDate date(String key) {
        return yamlMapping.date(key);
    }

    @Override
    public LocalDateTime dateTime(String key) {
        return yamlMapping.dateTime(key);
    }

    @Override
    public Map<String, Node> getMap() {
        Map<String, Node> map = new HashMap<>();

        for (YamlNode key : yamlMapping.keys()) {
            map.put(key.asScalar().value(), YamlSequenceShiv.CONVERT.apply( yamlMapping.value(key)));
        }

        return map;
    }

    @Override
    public boolean isEmpty() {
        return yamlMapping.isEmpty();
    }

    @Override
    public Type type() {
        return Type.MAPPING;
    }

    @Override
    public Scalar asScalar() throws BadValueException, ClassCastException {
        throw Exceptions.INCORRECT_NODE_TYPE_SERIALIZATION;
    }

    @Override
    public Mapping asMapping() throws BadValueException, ClassCastException {
        return this;
    }

    @Override
    public Sequence asSequence() throws BadValueException, ClassCastException {
        throw Exceptions.INCORRECT_NODE_TYPE_SERIALIZATION;
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws ClassCastException {
        if (clazz == YamlNode.class) {
            return clazz.cast(yamlMapping);
        }

        throw Exceptions.INCORRECT_NODE_TYPE_SERIALIZATION;

    }
}
