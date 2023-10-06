package yuukonfig.yaml.serializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yuukonfig.core.YuuKonfig;
import yuukonfig.core.annotate.Section;
import yuukonfig.core.err.BadValueException;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulation;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.Priority;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonstants.GenericPath;

import java.lang.reflect.Type;
import java.util.Objects;

public class SerializerFallbackTest {

    @Test
    public void testSerializerShouldUseFallback() {


        Node node = new ExtendProvider().create().test().serializeTest(ConfigWithNoDefaultValue.class);

        Assertions.assertEquals("hi,bye", node.asMapping().string("dto"));
    }

    public interface ConfigWithNoDefaultValue extends Section {

        DTO dto();

    }

    public static class DTOManipulator implements Manipulator {

        static {
            YuuKonfig.instance().register(DTOManipulator::new);
        }

        final Class<?> useClass;
        final RawNodeFactory factory;

        public DTOManipulator(Manipulation manipulation, Class<?> aClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
            this.useClass = aClass;
            this.factory = factory;
        }

        @Override
        public int handles() {
            if (useClass.equals(DTO.class)) return Priority.HANDLE;

            return Priority.DONT_HANDLE;
        }

        @Override
        public Object deserialize(Node node, GenericPath exceptionalKey) throws BadValueException {
            throw new IllegalStateException("unsupported");
        }

        @Override
        public Node serializeObject(Object object, String[] comment) {

            DTO dto = (DTO) object;

            return factory.scalarOf(String.format("%s,%s", dto.value1, dto.value2));
        }

        @Override
        public Node serializeDefault(String[] comment) {
            return factory.scalarOf("hi,bye");
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
