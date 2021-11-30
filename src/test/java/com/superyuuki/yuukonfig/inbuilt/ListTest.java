package com.superyuuki.yuukonfig.inbuilt;

import com.amihaiemil.eoyaml.Node;
import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.Section;
import com.superyuuki.yuukonfig.TestHelper;
import com.superyuuki.yuukonfig.impl.load.BaseRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

    static final String COMPLEX_CONFIG = """
            
            subsections:
              -
                hello: 0
                goodbye: bye
              -
                hello: 5
                goodbye: getOutOfMyHouse
            
            
            """;
    static final String DEFAULT_CONFIG = """
            
            stringsList:
              - hi
              - bye
            integerList:
              - 4
              - 3
            
            """;

    @Test
    public void testSerializingDefaultList() {
        YamlNode node = TestHelper.serializerTest(DefaultListConfig.class);

        Assertions.assertEquals(Node.SEQUENCE, node.asMapping().value("stringsList").type());
        Assertions.assertEquals(3, node.asMapping().value("integerList").asSequence().integer(1));

    }


    public interface DefaultListConfig extends Section {

        default List<String> stringsList() {
            List<String> list = new ArrayList<>();

            list.add("hi");
            list.add("bye");

            return list;
        }

        default List<Integer> integerList() {
            List<Integer> list = new ArrayList<>();

            list.add(4);
            list.add(3);

            return list;
        }

    }

    @Test
    public void testSerializingComplexList() {
        YamlNode node = TestHelper.serializerTest(ComplexListConfig.class);

        Assertions.assertEquals(Node.SEQUENCE, node.asMapping().value("subsections").type());
        Assertions.assertEquals("getOutOfMyHouse", node.asMapping().value("subsections").asSequence().yamlMapping(1).string("goodbye"));
    }

    public interface ComplexListConfig extends Section {
        interface SubSection extends Section {
            Integer hello();
            String goodbye();
        }

        default List<SubSection> subsections() {
            List<SubSection> moaningNoises = new ArrayList<>();

            moaningNoises.add(new SubSection() {
                @Override
                public Integer hello() {
                    return 0;
                }

                @Override
                public String goodbye() {
                    return "bye";
                }
            });

            moaningNoises.add(new SubSection() {
                @Override
                public Integer hello() {
                    return 5;
                }

                @Override
                public String goodbye() {
                    return "getOutOfMyHouse";
                }
            });

            return moaningNoises;
        }
    }

    @Test
    public void testDeserializingDefaultList() {
        DefaultListConfig config = TestHelper.deserializerTest(DEFAULT_CONFIG, DefaultListConfig.class);

        Assertions.assertEquals(4, config.integerList().get(0));
    }

    @Test
    public void testDeserializingComplexList() {
        ComplexListConfig config = TestHelper.deserializerTest(COMPLEX_CONFIG, ComplexListConfig.class);

        Assertions.assertEquals(0, config.subsections().get(0).hello());
    }

}
