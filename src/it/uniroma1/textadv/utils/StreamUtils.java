package it.uniroma1.textadv.utils;

import java.util.function.Consumer;
import java.util.function.Function;

public class StreamUtils {
    @SuppressWarnings("unchecked")
    public static <X extends Exception, T, R> void wrap(Consumer<StreamCheckedExecutor<X, T, R>> action) throws X {
        try {
            action.accept(body -> (argument) -> {
                try {
                    return body.apply(argument);
                } catch (Exception x) {
                    throw new StreamUncheckedException(x);
                }
            });
        } catch (StreamUncheckedException e) {
            throw (X) e.getCause();
        }
    }

    public interface StreamCheckedExecutor<X extends Exception, T, R> {
        Function<T, R> wrap(StreamCheckedExecutorBody<X, T, R> body);
    }

    @FunctionalInterface
    public interface StreamCheckedExecutorBody<X extends Exception, T, R> {
        R apply(T argument) throws X;
    }

    private static class StreamUncheckedException extends RuntimeException {
        public StreamUncheckedException(Exception cause) {
            super(cause);
        }
    }
}
