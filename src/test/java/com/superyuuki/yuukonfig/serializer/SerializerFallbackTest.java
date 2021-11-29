package com.superyuuki.yuukonfig.serializer;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.CommonRegistry;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.compose.Serializers;
import com.superyuuki.yuukonfig.compose.TypedSerializer;
import com.superyuuki.yuukonfig.BaseRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SerializerFallbackTest {

    @Test
    public void testSerializerShouldUseFallback() {
        YamlNode node = testRegistry().makeSerializers().serializeDefault(ConfigWithNoDefaultValue.class);

        Assertions.assertEquals("hi,bye", node.asMapping().string("dto"));
    }

    public interface ConfigWithNoDefaultValue extends Section {

        DTO dto();

    }

    static CommonRegistry testRegistry() {
        CommonRegistry registry = BaseRegistry.defaults();

        registry.register(new TypedSerializer.Mock<>(DTO.class, new DTOSerializer()), null); //excuse my null

        return registry;
    }

    public static class DTOSerializer implements TypedSerializer<DTO> {

        @Override
        public YamlNode serializeDefault(Class<? extends DTO> request, Serializers serializers) {
            return Yaml.createYamlScalarBuilder().addLine("hi,bye").buildPlainScalar();
        }

        @Override
        public YamlNode serializeObject(Class<? extends DTO> request, DTO object, Serializers serializers) {
            return Yaml.createYamlScalarBuilder().addLine(String.format("%s,%s", object.value1, object.value2)).buildPlainScalar();
        }
    }

    public static class DTO {
        private final String value1;
        private final String value2;

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

            if (value1 != null ? !value1.equals(dto.value1) : dto.value1 != null) return false;
            return value2 != null ? value2.equals(dto.value2) : dto.value2 == null;
        }

        @Override
        public int hashCode() {
            int result = value1 != null ? value1.hashCode() : 0;
            result = 31 * result + (value2 != null ? value2.hashCode() : 0);
            return result;
        }
    }

}
