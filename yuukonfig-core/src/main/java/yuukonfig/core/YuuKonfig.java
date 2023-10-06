package yuukonfig.core;

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
            provider = null;
        } else {
            List<YuuKonfigProvider> providers = new ArrayList<>();
            do {
                providers.add(it.next());
            } while (it.hasNext());

            providers.sort(((Comparator<YuuKonfigProvider>) (o1, o2) -> o1.priority() - o2.priority()).reversed());

            provider = providers.get(0);
        }

        if (provider == null) {
            throw new IllegalArgumentException("No YuuKonfig provider loaded!!");
        }

        return provider.create();
    }

    public static YuuKonfigAPI instance() {
        return REGISTRAR;
    }

}
