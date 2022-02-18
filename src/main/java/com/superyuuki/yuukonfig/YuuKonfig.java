package com.superyuuki.yuukonfig;

import java.util.*;

public class YuuKonfig {

    private static final YuuKonfigAPI REGISTRAR;

    private YuuKonfig() {}

    static {
        REGISTRAR = getDefiner();
    }

    private static YuuKonfigAPI getDefiner() {
        ClassLoader classLoader = YuuKonfig.class.getClassLoader();
        ServiceLoader<YuuKonfigProvider> loader = ServiceLoader.load(YuuKonfigProvider.class, classLoader);
        Iterator<YuuKonfigProvider> it = loader.iterator();
        if (!it.hasNext()) {
            throw new IllegalStateException("No provider was found for yuukonfig!");
        }
        List<YuuKonfigProvider> providers = new ArrayList<>();
        do {
            providers.add(it.next());
        } while (it.hasNext());

        providers.sort(((Comparator<YuuKonfigProvider>) (o1, o2) -> o1.priority() - o2.priority()).reversed()); // reverse to use descending order

        return providers.get(0).create();
    }

    public static YuuKonfigAPI instance() {
        return REGISTRAR;
    }

}
