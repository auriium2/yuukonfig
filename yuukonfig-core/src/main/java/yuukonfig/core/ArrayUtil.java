package yuukonfig.core;

import java.util.Arrays;

public class ArrayUtil {

    @SafeVarargs public static <T> T[] combine(T[] original, T... continuous) {
        T[] newArray = Arrays.copyOf(original, original.length + continuous.length);

        System.arraycopy(continuous, 0, newArray, original.length, original.length + continuous.length - original.length);

        return newArray;
    }

}
