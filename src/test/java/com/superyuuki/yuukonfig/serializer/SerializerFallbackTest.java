package com.superyuuki.yuukonfig.serializer;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.BadValueException;
import com.superyuuki.yuukonfig.BaseProvider;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.manipulation.Contextual;
import com.superyuuki.yuukonfig.manipulation.Manipulation;
import com.superyuuki.yuukonfig.manipulation.Manipulator;
import com.superyuuki.yuukonfig.manipulation.Priority;
import com.superyuuki.yuukonfig.user.Section;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

public class SerializerFallbackTest {

    @Test
    public void testSerializerShouldUseFallback() {


        YamlNode node = new ExtendProvider().create().test().serializeTest(ConfigWithNoDefaultValue.class);

        Assertions.assertEquals("hi,bye", node.asMapping().string("dto"));
    }

    public interface ConfigWithNoDefaultValue extends Section {

        DTO dto();

    }

    public static class DTOManipulator implements Manipulator {

        static {
            YuuKonfig.instance().register(DTOManipulator::new);
        }

        private final Class<?> useClass;

        public DTOManipulator(Manipulation manipulation, Class<?> aClass, Contextual<Type> typeContextual) {
            this.useClass = aClass;
        }

        @Override
        public int handles() {
            if (useClass.equals(DTO.class)) return Priority.HANDLE;

            return Priority.DONT_HANDLE;
        }

        @Override
        public Object deserialize(YamlNode node, String exceptionalKey) throws BadValueException {
            throw new IllegalStateException("unsupported");
        }

        @Override
        public YamlNode serializeObject(Object object, String[] comment) {

            DTO dto = (DTO) object;

            return Yaml.createYamlScalarBuilder().addLine(String.format("%s,%s", dto.value1, dto.value2)).buildPlainScalar(Arrays.asList(comment), "");
        }

        @Override
        public YamlNode serializeDefault(String[] comment) {
            return Yaml.createYamlScalarBuilder().addLine("hi,bye").buildPlainScalar();
        }
    }

    public static class DTO {
        final String value1;
        final String value2;

        DTO(String value1, String value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        public String getValue1() {
            return value1;
        }

        public String getValue2() {
            return value2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DTO dto = (DTO) o;

            if (!Objects.equals(value1, dto.value1)) return false;
            return Objects.equals(value2, dto.value2);
        }

        @Override
        public int hashCode() {
            int result = value1 != null ? value1.hashCode() : 0;
            result = 31 * result + (value2 != null ? value2.hashCode() : 0);
            return result;
        }
    }

}
