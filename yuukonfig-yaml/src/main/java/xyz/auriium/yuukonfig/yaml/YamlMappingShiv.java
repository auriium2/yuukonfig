package xyz.auriium.yuukonfig.yaml;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlNode;
import xyz.auriium.yuukonfig.core.err.BadValueException;
import xyz.auriium.yuukonfig.core.node.Mapping;
import xyz.auriium.yuukonfig.core.node.Node;
import xyz.auriium.yuukonfig.core.node.Scalar;
import xyz.auriium.yuukonfig.core.node.Sequence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

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
    public boolean isEmpty() {
        return yamlMapping.isEmpty();
    }

    @Override
    public Type type() {
        return Type.MAPPING;
    }

    @Override
    public Scalar asScalar() throws BadValueException, ClassCastException {
        throw new ClassCastException("this is a mapping");
    }

    @Override
    public Mapping asMapping() throws BadValueException, ClassCastException {
        return this;
    }

    @Override
    public Sequence asSequence() throws BadValueException, ClassCastException {
        throw new ClassCastException("this is a mapping");
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws ClassCastException {
        if (clazz == YamlNode.class) {
            return clazz.cast(yamlMapping);
        }

        throw new ClassCastException("invalid access of unexpected type");

    }
}
