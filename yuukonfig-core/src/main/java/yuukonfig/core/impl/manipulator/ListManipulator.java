package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;

import yuukonfig.core.err.MissingGenericException;
import yuukonfig.core.err.MissingTypeException;
import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.impl.TypeUtil;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Priority;
import xyz.auriium.yuukonstants.GenericPath;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListManipulator implements Manipulator {

    final BaseManipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;
    final Contextual<Type> type;

    public ListManipulator(BaseManipulation manipulation, Class<?> useClass, Contextual<Type> type, RawNodeFactory factory) {
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
    public Object deserialize(Node node) throws BadValueException {
        Class<?> parseAs = getGenericType(node.path());

        List<Object> uncheckedList = new ArrayList<>();


        //contextual
        //Type genericParameter0OfThisClass = ((ParameterizedType) parseAs.getGenericSuperclass()).getActualTypeArguments()[0];

        for (Node child : node.asSequence()) {
            Object toAdd = manipulation.deserialize(child, parseAs, Contextual.empty()); //TODO deeper contextual searches

            uncheckedList.add(toAdd);
        }

        return List.copyOf(uncheckedList);
    }

    @Override
    public Node serializeObject(Object object, GenericPath path) {
        List<?> list = (List<?>) object; //handles should make sure nothing that is not a list is given to this object

        //TODO contextual generic key
        RawNodeFactory.SequenceBuilder builder = factory.makeSequenceBuilder(path);

        Class<?> as = getGenericTypeSer();


        for (Object subject : list) {
            //TODO sameness check
            Node toAdd = manipulation.serialize(subject, as, path); //List<List<T>> wont work

            builder.add(toAdd);
        }


        return builder.build();
    }

    @Override
    public Node serializeDefault(GenericPath path) {
        return factory.makeSequenceBuilder(path).build();
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

    Class<?> getGenericType(GenericPath key) {
        if (!type.present()) throw new MissingTypeException(key);

        Class<?> actualTypeArgument;
        try {
            actualTypeArgument = (Class<?>) ((ParameterizedType) type.get()).getActualTypeArguments()[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MissingGenericException(manipulation.configName(), key, 1);
        }

        return actualTypeArgument;
    }
}
