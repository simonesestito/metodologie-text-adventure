package it.uniroma1.textadv.utils;

public class Lazy<T, X extends Throwable> {
    private final Builder<T, X> supplier;
    private T object;

    public Lazy(Builder<T, X> supplier) {
        this.supplier = supplier;
    }

    public T get() throws X {
        if (object == null)
            object = supplier.build();
        return object;
    }

    public interface Builder<T, X extends Throwable> {
        T build() throws X;
    }
}
