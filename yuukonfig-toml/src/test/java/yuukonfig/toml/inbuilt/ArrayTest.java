package yuukonfig.toml.inbuilt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yuukonfig.core.YuuKonfig;
import yuukonfig.core.annotate.Section;
import yuukonfig.core.node.Node;
import yuukonstants.exception.ExceptionUtil;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.UUID;

public class ArrayTest {

    public interface ArrayConfig extends Section {

        default String[] stringArray() {
            return new String[] {"a", "c", "b"};
        }

        default int[] basicArray() {
            return new int[] {1,2,3,4,6,5};
        }

        default int[] emptyArray() {
            return new int[] {};
        }

        default List<String[]> complexArray() {

            return List.of(
                    new String[] { "a", "b"},
                    new String[] { "c", "d" }
            );
        }
    }


    public interface ArrayConfig2 extends Section {


        int[] basicArray();
        int[] emptyArray();
        String[] stringArray();
        List<String[]> complexArray();


    }
    static final String TOML = """
            stringArray = ["h","i"]
            basicArray = [3,4]
            emptyArray = []
            complexArray = [["a", "c"],["b", "d"]]
            """;


    @Test
    public void testDeserializingArray() {

        ExceptionUtil.wrapExceptionalRunnable(() -> {
            try {
                ArrayConfig2 config = YuuKonfig.instance().test().deserializeTest(TOML, ArrayConfig2.class);


                Assertions.assertEquals(2, config.basicArray().length);
                Assertions.assertEquals(0, config.emptyArray().length);
                Assertions.assertEquals(2, config.stringArray().length);

                Assertions.assertEquals("b", config.complexArray().get(1)[0]);

            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

        }).run();



    }


    @Test
    public void testSerializingArray() {
        Node node = YuuKonfig.instance().test().serializeTest(ArrayConfig.class);

        Assertions.assertEquals(0, node.asMapping().yamlSequence("emptyArray").getList().size());
        Assertions.assertEquals(6, node.asMapping().yamlSequence("basicArray").getList().size());
        Assertions.assertEquals(3, node.asMapping().yamlSequence("stringArray").getList().size());


        Assertions.assertEquals("a", node.asMapping().yamlSequence("stringArray").getList().get(0).asScalar().value());
        Assertions.assertEquals("c", node.asMapping().yamlSequence("stringArray").getList().get(1).asScalar().value());

        Assertions.assertEquals("6", node.asMapping().yamlSequence("basicArray").getList().get(4).asScalar().value());
        Assertions.assertEquals("5", node.asMapping().yamlSequence("basicArray").getList().get(5).asScalar().value());

        Assertions.assertEquals("c", node
                .asMapping()
                .yamlSequence("complexArray")
                .getList()
                .get(1)
                .asSequence()
                .getList()
                .get(0)
                .asScalar()
                .value()
        );

    }


}
