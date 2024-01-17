package yuukonfig.core.impl.manipulator;

import yuukonfig.core.err.BadValueException;
import yuukonfig.core.err.Exceptions;
import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.manipulation.Manipulator;
import yuukonfig.core.manipulation.Priority;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonstants.GenericPath;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;

public class ArrayManipulator implements Manipulator {

    final BaseManipulation manipulation;
    final Class<?> useClass;
    final RawNodeFactory factory;
    final Contextual<Type> type;

    public ArrayManipulator(BaseManipulation manipulation, Class<?> useClass, Contextual<Type> type, RawNodeFactory factory) {
        this.manipulation = manipulation;
        this.useClass = useClass;
        this.type = type;
        this.factory = factory;
    }

    public int handles() {
        return useClass.isArray() ? Priority.HANDLE : Priority.DONT_HANDLE;
    }

    @Override
    public Object deserialize(Node node) throws BadValueException {
        Class<?> componentType = useClass.componentType();
        List<Node> contents = node.asSequence().getList();


        //TODO MORE SHITTY HACKS I NEED TO FIX
        if (!componentType.isPrimitive()) {
            Object[] out = (Object[]) Array.newInstance(componentType, contents.size());

            for (int i = 0; i < contents.size(); i++) {
                out[i] = manipulation.deserialize(contents.get(i), componentType);
            }

            return out;
        }
        
        if (componentType == int.class) {
            int[] array = new int[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (int) manipulation.deserialize(contents.get(i), componentType);
            }

            return array;
        }

        if (componentType == byte.class) {
            byte[] array = new byte[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (byte) manipulation.deserialize(contents.get(i), componentType);
            }

            return array;
        }

        if (componentType == short.class) {
            short[] array = new short[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (short) manipulation.deserialize(contents.get(i), componentType);
            }

            return array;
        }

        if (componentType == long.class) {
            long[] array = new long[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (long) manipulation.deserialize(contents.get(i), componentType);
            }

            return array;
        }
        if (componentType == double.class) {
            double[] array = new double[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (double) manipulation.deserialize(contents.get(i), componentType);
            }

            return array;
        }

        if (componentType == float.class) {
            float[] array = new float[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (float) manipulation.deserialize(contents.get(i), componentType);
            }

            return array;
        }

        if (componentType == boolean.class) {
            boolean[] array = new boolean[contents.size()];

            for (int i = 0; i < contents.size(); i++) {
                array[i] = (boolean) manipulation.deserialize(contents.get(i), componentType);
            }

            return array;
        }


        throw Exceptions.GENERIC_WTF_EXCEPTION();
    }
    

    @Override
    public Node serializeObject(Object object, GenericPath path) {
        //System.out.println(object.getClass().getSimpleName());
        Class<?> componentType = useClass.componentType();

        if (!componentType.isPrimitive()) {
            Object[] asObjectArray = (Object[]) object;

            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder(path);

            for (Object subject : asObjectArray) {
                builder.addSerialize(manipulation::serializeCtx, subject, componentType);
            }
            return builder.build();
        }

        //SHITTY HACK
        
        if (componentType == int.class) {
            int[] asArray = (int[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder(path);
            for (int subject : asArray) {
                builder.addSerialize(manipulation::serializeCtx, subject, int.class);
            }
            return builder.build();
        }

        if (componentType == double.class) {
            double[] asArray = (double[])object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder(path);
            for (double subject : asArray) {
                builder.addSerialize(manipulation::serializeCtx, subject, double.class);
            }
            return builder.build();
        }

        if (componentType == float.class) {
            float[] asArray = (float[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder(path);
            for (float subject : asArray) {
                builder.addSerialize(manipulation::serializeCtx, subject, float.class);
            }
            return builder.build();
        }

        if (componentType == long.class) {
            long[] asArray = (long[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder(path);
            for (long subject : asArray) {
                builder.addSerialize(manipulation::serializeCtx, subject, long.class);
            }
            return builder.build();
        }

        if (componentType == short.class) {
            short[] asArray = (short[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder(path);
            for (short subject : asArray) {
                builder.addSerialize(manipulation::serializeCtx, subject, short.class);
            }
            return builder.build();
        }

        if (componentType == boolean.class) {
            boolean[] asArray = (boolean[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder(path);
            for (boolean subject : asArray) {
                builder.addSerialize(manipulation::serializeCtx, subject, boolean.class);
            }
            return builder.build();
        }

        if (componentType == byte.class) {
            byte[] asArray = (byte[]) object;
            RawNodeFactory.SequenceBuilder builder = this.factory.makeSequenceBuilder(path);
            for (byte subject : asArray) {
                builder.addSerialize(manipulation::serializeCtx, subject, byte.class);
            }
            return builder.build();
        }


        throw new IllegalStateException("how did you skip every primitive type?");
    }

    @Override
    public Node serializeDefault(GenericPath path) {
        return this.factory.makeSequenceBuilder(path).build();
    }


}
