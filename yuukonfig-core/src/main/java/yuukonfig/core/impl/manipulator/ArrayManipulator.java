package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulation;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.Priority;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import yuukonstants.GenericPath;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayManipulator implements Manipulator {

    final Manipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;
    final Contextual<Type> type;

    public ArrayManipulator(Manipulation manipulation, Class<?> useClass, Contextual<Type> type, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.useClass = useClass;
        this.type = type;
        this.factory = factory;
    }

    public int handles() {
        return useClass.isArray() ? Priority.HANDLE : Priority.DONT_HANDLE;
    }

    public Object deserialize(Node node, GenericPath exceptionalKey) throws BadValueException {
        Class<?> componentType = useClass.componentType();
        List<Node> contents = node.asSequence().getList();


        //TODO MORE SHITTY HACKS I NEED TO FIX
        if (!componentType.isPrimitive()) {
            Object[] out = (Object[]) Array.newInstance(componentType, contents.size());

            for (int i = 0; i < contents.size(); i++) {
                out[i] = manipulation.deserialize(contents.get(i), exceptionalKey.append(i + ""), componentType);
            }

            return out;
        }
        
        if (componentType == int.class) {
            int[] array = new int[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (int) manipulation.deserialize(contents.get(i), exceptionalKey.append(i + ""), componentType);
            }

            return array;
        }

        if (componentType == byte.class) {
            byte[] array = new byte[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (byte) manipulation.deserialize(contents.get(i), exceptionalKey.append(i + ""), componentType);
            }

            return array;
        }

        if (componentType == short.class) {
            short[] array = new short[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (short) manipulation.deserialize(contents.get(i), exceptionalKey.append(i + ""), componentType);
            }

            return array;
        }

        if (componentType == long.class) {
            long[] array = new long[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (long) manipulation.deserialize(contents.get(i), exceptionalKey.append(i + ""), componentType);
            }

            return array;
        }

        if (componentType == double.class) {
            double[] array = new double[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (double) manipulation.deserialize(contents.get(i), exceptionalKey.append(i + ""), componentType);
            }

            return array;
        }

        if (componentType == float.class) {
            float[] array = new float[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (float) manipulation.deserialize(contents.get(i), exceptionalKey.append(i + ""), componentType);
            }

            return array;
        }

        if (componentType == boolean.class) {
            boolean[] array = new boolean[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (boolean) manipulation.deserialize(contents.get(i), exceptionalKey.append(i + ""), componentType);
            }

            return array;
        }


        throw new IllegalStateException("what the fuck are you doing");





    }

    public Node serializeObject(Object object, String[] comment) {
        //System.out.println(object.getClass().getSimpleName());
        Class<?> componentType = useClass.componentType();

        if (!componentType.isPrimitive()) {
            Object[] asObjectArray = (Object[]) object;

            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder();

            for (int i = 0; i < asObjectArray.length; i++) {
                Object subject = asObjectArray[i];
                Node toAdd = this.manipulation.serialize(subject, componentType, new String[0], Contextual.empty());
                builder.add(toAdd);
            }
            return builder.build(comment);
        }

        //SHITTY HACK
        
        if (componentType == int.class) {
            int[] asArray = (int[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder();
            for (int subject : asArray) {
                Node toAdd = this.manipulation.serialize(subject, int.class, new String[0], Contextual.empty());
                builder.add(toAdd);
            }
            return builder.build(comment);
        }

        if (componentType == double.class) {
            double[] asArray = (double[])object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder();
            for (double subject : asArray) {
                Node toAdd = this.manipulation.serialize(subject, double.class, new String[0], Contextual.empty());
                builder.add(toAdd);
            }
            return builder.build(comment);
        }

        if (componentType == float.class) {
            float[] asArray = (float[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder();
            for (float subject : asArray) {
                Node toAdd = this.manipulation.serialize(subject, float.class, new String[0], Contextual.empty());
                builder.add(toAdd);
            }
            return builder.build(comment);
        }

        if (componentType == long.class) {
            long[] asArray = (long[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder();
            for (long subject : asArray) {
                Node toAdd = this.manipulation.serialize(subject, long.class, new String[0], Contextual.empty());
                builder.add(toAdd);
            }
            return builder.build(comment);
        }

        if (componentType == short.class) {
            short[] asArray = (short[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder();
            for (short subject : asArray) {
                Node toAdd = this.manipulation.serialize(subject, short.class, new String[0], Contextual.empty());
                builder.add(toAdd);
            }
            return builder.build(comment);
        }

        if (componentType == boolean.class) {
            boolean[] asArray = (boolean[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder();
            for (boolean subject : asArray) {
                Node toAdd = this.manipulation.serialize(subject, boolean.class, new String[0], Contextual.empty());
                builder.add(toAdd);
            }
            return builder.build(comment);
        }

        if (componentType == byte.class) {
            byte[] asArray = (byte[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder();
            for (byte subject : asArray) {
                Node toAdd = this.manipulation.serialize(subject, byte.class, new String[0], Contextual.empty());
                builder.add(toAdd);
            }
            return builder.build(comment);
        }


        throw new IllegalStateException("how did you skip every primitive type?");
    }

    public Node serializeDefault(String[] comment) {
        return this.factory.makeSequenceBuilder().build(comment);
    }


}
