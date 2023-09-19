package xyz.auriium.yuukonfig.core.impl.manipulator;

import xyz.auriium.yuukonfig.core.err.BadValueException;

import xyz.auriium.yuukonfig.core.err.MissingGenericException;
import xyz.auriium.yuukonfig.core.err.MissingTypeException;
import xyz.auriium.yuukonfig.core.node.Node;
import xyz.auriium.yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonfig.core.manipulation.Contextual;
import xyz.auriium.yuukonfig.core.manipulation.Manipulation;
import xyz.auriium.yuukonfig.core.manipulation.Manipulator;
import xyz.auriium.yuukonfig.core.manipulation.Priority;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListManipulator implements Manipulator {

    final Manipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;
    final Contextual<Type> type;

    public ListManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> type, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.useClass = useClass;
        this.type = type;
        this.factory = factory;
    }

    @Override
    public int handles() {
        if (List.class.isAssignableFrom(useClass)) {
            return Priority.HANDLE;
        }

        return Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(Node node, String exceptionalKey) throws BadValueException {
        Class<?> parseAs = getGenericType(exceptionalKey);

        List<Object> uncheckedList = new ArrayList<>();

        //contextual
        //Type genericParameter0OfThisClass = ((ParameterizedType) parseAs.getGenericSuperclass()).getActualTypeArguments()[0];

        for (Node child : node.asSequence()) {
            Object toAdd = manipulation.deserialize(child, exceptionalKey, parseAs, Contextual.empty()); //TODO deeper contextual searches

            uncheckedList.add(toAdd);
        }

        return List.copyOf(uncheckedList);
    }

    @Override
    public Node serializeObject(Object object, String[] comment) {
        List<?> list = (List<?>) object; //handles should make sure nothing that is not a list is given to this object

        //TODO contextual generic key
        RawNodeFactory.SequenceBuilder builder = factory.makeSequenceBuilder();

        Class<?> as = getGenericTypeSer();


        for (Object subject : list) {
            //TODO sameness check
            Node toAdd = manipulation.serialize(subject, as, new String[]{}, Contextual.empty()); //List<List<T>> wont work

            builder.add(toAdd);
        }


        return builder.build(comment);
    }

    @Override
    public Node serializeDefault(String[] comment) {
        return factory.makeSequenceBuilder().build(comment);
    }

    Class<?> getGenericTypeSer() {
        if (!type.present()) throw new IllegalStateException("No type defined for list!");

        Class<?> actualTypeArgument;
        try {
            actualTypeArgument = (Class<?>) ((ParameterizedType) type.get()).getActualTypeArguments()[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalStateException("not enough arguments for generic in"  + manipulation.configName());
        }

        return actualTypeArgument;
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
