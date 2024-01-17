package yuukonfig.toml.serializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yuukonfig.core.YuuKonfig;
import yuukonfig.core.annotate.Section;
import yuukonfig.core.err.BadValueException;
import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.Priority;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonstants.GenericPath;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class OrderedTests {

    static final String CONFIG_LIST = """
              
                [nestedConfig]
                list = [1,2,3,4,5,6,7,8,9,10]
                  
                """;

    static final String CONFIG_MAP = """
              
                [mapp]
                a = 2
                b = 3
                c = 4
                d = 5
                  
                """;

    interface ListTest extends Section {
        NestedConfig nestedConfig();

        interface NestedConfig extends Section {
            HoldsList list();
        }
    }

    interface MapTest extends Section {
        HoldsMap mapp();
    }



    public record HoldsMap(Map<String, Node> map) { }
    public record HoldsList(List<Node> list) {}

    static class DeserializeHoldsList implements Manipulator {

        final Class<?> clazz;

        public DeserializeHoldsList(BaseManipulation manipulation, Class<?> aClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
            this.clazz = aClass;
        }


        @Override
        public int handles() {
            if (clazz == HoldsList.class) return Priority.HANDLE;
            return Priority.DONT_HANDLE;
        }

        @Override
        public Object deserialize(Node node) throws BadValueException {
            return new HoldsList(node.asSequence().getList());
        }

        @Override
        public Node serializeObject(Object object, GenericPath path) {
            throw new IllegalStateException("not tested");
        }

        @Override
        public Node serializeDefault(GenericPath path) {
            throw new IllegalStateException("not tested");
        }
    }

    static class DeserializeHoldsMap implements Manipulator {

        final Class<?> clazz;

        public DeserializeHoldsMap(BaseManipulation manipulation, Class<?> aClass, Contextual<Type> typeContextual, RawNodeFactory factory) {
            this.clazz = aClass;
        }


        @Override
        public int handles() {
            if (clazz == HoldsMap.class) return Priority.HANDLE;
            return Priority.DONT_HANDLE;
        }

        @Override
        public Object deserialize(Node node) throws BadValueException {
            return new HoldsMap(node.asMapping().getMap());
        }

        @Override
        public Node serializeObject(Object object, GenericPath path) {
            throw new IllegalStateException("not tested");
        }

        @Override
        public Node serializeDefault(GenericPath path) {
            throw new IllegalStateException("not tested");
        }
    }

    interface ConfigList extends Section {

    }

    @Test
    public void testOrderedListIsOrdered() throws IOException {
        ListTest listVariant = YuuKonfig
                .instance()
                .register(DeserializeHoldsList::new)
                .register(DeserializeHoldsMap::new)
                .test()
                .deserializeTest(CONFIG_LIST, ListTest.class);

        HoldsList l = listVariant.nestedConfig().list();

        Assertions.assertEquals(1, Integer.valueOf(l.list.get(0).asScalar().value()));
        Assertions.assertEquals(6, Integer.valueOf(l.list.get(5).asScalar().value()));
        Assertions.assertEquals(10, Integer.valueOf(l.list.get(9).asScalar().value()));
        Assertions.assertEquals(10, l.list.size());

        MapTest mapVariant = YuuKonfig
                .instance()
                .register(DeserializeHoldsList::new)
                .register(DeserializeHoldsMap::new)
                .test()
                .deserializeTest(CONFIG_MAP, MapTest.class);

        HoldsMap m = mapVariant.mapp();

        Assertions.assertEquals("2", m.map.get("a").asScalar().value());
        Assertions.assertEquals("4", m.map.get("c").asScalar().value());
        Assertions.assertEquals(4, m.map.size());



    }


}
