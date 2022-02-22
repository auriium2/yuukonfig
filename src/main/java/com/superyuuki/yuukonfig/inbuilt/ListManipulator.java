package com.superyuuki.yuukonfig.inbuilt;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlNode;
import com.amihaiemil.eoyaml.YamlSequenceBuilder;
import com.superyuuki.yuukonfig.BadValueException;
import com.superyuuki.yuukonfig.YuuKonfig;
import com.superyuuki.yuukonfig.manipulation.Contextual;
import com.superyuuki.yuukonfig.manipulation.Manipulation;
import com.superyuuki.yuukonfig.manipulation.Manipulator;
import com.superyuuki.yuukonfig.manipulation.Priority;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListManipulator implements Manipulator {

    private final Manipulation manipulation;
    private final Class<?> useClass;
    private final Contextual<Type> type;

    public ListManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> type) {
        this.manipulation = manipulation;
        this.useClass = useClass;
        this.type = type;
    }

    @Override
    public int handles() {
        if (List.class.isAssignableFrom(useClass)) {
            return Priority.HANDLE;
        }

        return Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(YamlNode node, String exceptionalKey) throws BadValueException {
        Class<?> parseAs = getGenericType(exceptionalKey);

        List<Object> uncheckedList = new ArrayList<>();

        //contextual
        //Type genericParameter0OfThisClass = ((ParameterizedType) parseAs.getGenericSuperclass()).getActualTypeArguments()[0];

        for (YamlNode child : node.asSequence()) {
            Object toAdd = manipulation.deserialize(child, exceptionalKey, parseAs, Contextual.empty()); //TODO deeper contextual searches

            uncheckedList.add(toAdd);
        }

        return List.copyOf(uncheckedList);
    }

    @Override
    public YamlNode serializeObject(Object object, String[] comment) {
        List<?> list = (List<?>) object; //handles should make sure nothing that is not a list is given to this object

        //TODO contextual generic key
        YamlSequenceBuilder listBuilder = Yaml.createYamlSequenceBuilder();

        for (Object subject : list) {
            //TODO sameness check
            YamlNode toAdd = manipulation.serialize(subject, new String[]{}, Contextual.empty()); //List<List<T>> wont work

            listBuilder = listBuilder.add(toAdd);
        }


        return listBuilder.build(Arrays.asList(comment));
    }

    @Override
    public YamlNode serializeDefault(String[] comment) {
        return Yaml.createYamlSequenceBuilder().build(Arrays.asList(comment));
    }

    Class<?> getGenericType(String key) {
        if (!type.present()) throw new MissingTypeException(manipulation.configName(), key);

        Class<?> actualTypeArgument;
        try {
            actualTypeArgument = (Class<?>) ((ParameterizedType) type.get()).getActualTypeArguments()[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MissingGenericException(manipulation.configName(), key, 1);
        }

        return actualTypeArgument;
    }
}
