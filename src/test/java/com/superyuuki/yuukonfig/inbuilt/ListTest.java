package com.superyuuki.yuukonfig.inbuilt;

import com.amihaiemil.eoyaml.YamlNode;
import com.superyuuki.yuukonfig.BaseRegistry;
import com.superyuuki.yuukonfig.Section;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

    @Test
    public void testSerializingDefaultList() {
        YamlNode node = BaseRegistry.defaults().makeSerializers().serializeDefault(DefaultListConfig.class);


        System.out.println(node);
    }


    public interface DefaultListConfig extends Section {

        default List<String> stringsList() {
            List<String> list = new ArrayList<>();

            list.add("hi");

            return list;
        }

        default List<Integer> integerList() {
            List<Integer> list = new ArrayList<>();

            list.add(4);

            return list;
        }

    }

}
