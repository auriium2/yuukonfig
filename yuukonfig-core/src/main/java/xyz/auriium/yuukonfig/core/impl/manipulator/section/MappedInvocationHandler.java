package xyz.auriium.yuukonfig.core.impl.manipulator.section;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class MappedInvocationHandler implements InvocationHandler {

    private final Map<String, Object> map;

    MappedInvocationHandler(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getDeclaringClass() == Object.class) {
            if (method.getName().equals("equals")) {
                return proxy == args[0]; //See dazzleconf for explanation
            }

            try {
                return method.invoke(this, args);
            } catch (IllegalAccessException | IllegalArgumentException ex) {
                throw new AssertionError(ex);

            } catch (InvocationTargetException ex) {
                Throwable cause = ex.getCause();
                if (cause != null) {
                    throw cause;
                } else {
                    throw ex;
                }
            }
        }

        assert args == null : Arrays.deepToString(args);

        return map.get(method.getName());
    }
}
