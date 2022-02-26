package com.superyuuki.yuukonfig;

import com.superyuuki.yuukonfig.manipulation.ManipulatorConstructor;

import java.util.*;

public class YuuKonfig {

    private static YuuKonfigAPI REGISTRAR;

    static {
        REGISTRAR = getDefiner();
    }

    private YuuKonfig() {}

    private static YuuKonfigAPI getDefiner() {
        ClassLoader classLoader = YuuKonfig.class.getClassLoader();
        ServiceLoader<YuuKonfigProvider> loader = ServiceLoader.load(YuuKonfigProvider.class, classLoader);
        Iterator<YuuKonfigProvider> it = loader.iterator();

        YuuKonfigProvider provider;

        if (!it.hasNext()) {
            provider = new BaseProvider();
        } else {
            List<YuuKonfigProvider> providers = new ArrayList<>();
            do {
                providers.add(it.next());
            } while (it.hasNext());

            providers.sort(((Comparator<YuuKonfigProvider>) (o1, o2) -> o1.priority() - o2.priority()).reversed());

            provider = providers.get(0);
        }

        return provider.create();
    }

    public static YuuKonfigAPI instance() {
        return REGISTRAR;
    }

}
