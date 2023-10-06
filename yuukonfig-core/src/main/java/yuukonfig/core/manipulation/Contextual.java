package yuukonfig.core.manipulation;

public interface Contextual<T> {

    boolean present();
    T get();

    static <T> Contextual<T> present(T obj) {
        return new Present<>(obj);
    }

    static <T> Contextual<T> empty() {
        return new Not<>();
    }

    class Present<T> implements Contextual<T> {
        private final T thing;

        Present(T thing) {
            this.thing = thing;
        }

        @Override
        public boolean present() {
            return true;
        }

        @Override
        public T get() {
            return thing;
        }
    }

    class Not<T> implements Contextual<T> {

        Not() {}

        @Override
        public boolean present() {
            return false;
        }

        @Override
        public T get() {
            throw new UnsupportedOperationException("not present!");
        }
    }

}
