package it.uniroma1.textadv.utils;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Metodi di utilità per lavorare con gli {@link java.util.stream.Stream}
 */
public class StreamUtils {
    /**
     * Incapsula un'eccezione che uno stream può lanciare in {@link java.util.stream.Stream#map(Function)}
     * <p>
     * Nel caso originale, non è possibile lanciare un'eccezione da dentro {@link java.util.stream.Stream#map(Function)}.
     * In questo modo invece viene propagata all'esterno ed è utilizzabile in un normale {@link java.util.stream.Stream}.
     * <p>
     * Esempio:
     * <pre>
     * {@code
     * StreamUtils.<IOException, String, Command>wrap(checked ->
     *  commandsLines
     *      .map(checked.wrap(Command::of))
     *      .collect(Collectors.groupingBy(Command::getLiteralPrefix))
     *      .forEach(commands::add));
     * }
     * </pre>
     *
     * @param action Azione da eseguire che può lanciare un'eccezione di tipo checked
     * @param <X>    Tipo dell'eccezione
     * @param <T>    Tipo del parametro in ingresso
     * @param <R>    Tipo del parametro in ingresso
     * @throws X Eccezione lanciata durante l'esecuzione della funzione passata
     */
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

    /**
     * Incapsulatore della funzione di mapping, ora senza throws checked
     *
     * @param <X> Eccezione lanciabile dal vero mapping
     * @param <T> Tipo del parametro in ingresso
     * @param <R> Tipo del parametro in ingresso
     */
    public interface StreamCheckedExecutor<X extends Exception, T, R> {
        /**
         * Funzione che invoca la vera funzione di mapping, incapsulata
         *
         * @param body Incapsulamento della funzione di mapping
         * @return Funzione da poter utilizzare, con sostituita l'eccezione checked con una unchecked
         */
        Function<T, R> wrap(StreamCheckedExecutorBody<X, T, R> body);
    }

    /**
     * Funzione di mapping che può lanciare l'eccezione checked
     *
     * @param <X> Tipo dell'eccezione
     * @param <T> Tipo del parametro in ingresso
     * @param <R> Tipo del parametro in ingresso
     */
    @FunctionalInterface
    public interface StreamCheckedExecutorBody<X extends Exception, T, R> {
        /**
         * Esegui la funzione di mapping accettando la possibilità di avere l'eccezione checked
         *
         * @param argument Parametro della funzione di mapping sottostante
         * @return Risultato della funzione di mapping sottostante
         * @throws X Eccezione checked concessa
         */
        R apply(T argument) throws X;
    }

    /**
     * Eccezione privata di tipo unchecked che può essere propagata senza problemi all'indietro.
     * <p>
     * La causa verrà lanciata esternamente e catturata all'esterno di tutto il wrap.
     */
    private static class StreamUncheckedException extends RuntimeException {
        /**
         * Incapsula l'eccezione checked data
         *
         * @param cause Eccezione da incapsulare
         */
        public StreamUncheckedException(Exception cause) {
            super(cause);
        }
    }
}
