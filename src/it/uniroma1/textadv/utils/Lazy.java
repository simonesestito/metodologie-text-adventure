package it.uniroma1.textadv.utils;

/**
 * Esegui l'istanziazione di un oggetto in maniera lazy e una sola volta, gestendo l'eccezione.
 * @param <T> Tipo dell'oggetto
 * @param <X> Eccezione che si può verificare
 */
public class Lazy<T, X extends Throwable> {
    /**
     * Istanziazione dell'oggetto qaundo richiesto la prima volta
     */
    private final Builder<T, X> supplier;

    /**
     * Oggetto già creato
     */
    private T object;

    /**
     * Crea un nuovo inizializzatore lazy
     * @param supplier Funzione da eseguire per creare l'oggetto
     */
    public Lazy(Builder<T, X> supplier) {
        this.supplier = supplier;
    }

    /**
     * Ottieni l'oggetto e crealo se è la prima richiesta
     * @return Oggetto richiesto
     * @throws X Eccezione eventualmente lanciata in fase di creazione
     */
    public T get() throws X {
        if (object == null)
            object = supplier.build();
        return object;
    }

    /**
     * Interfaccia per la creazione di un oggetto con possibilità di lanciare un'eccezione
     * @param <T> Tipo dell'oggetto
     * @param <X> Eccezione che si può verificare
     */
    @FunctionalInterface
    public interface Builder<T, X extends Throwable> {
        /**
         * Esegui la creazione dell'oggetto, permettendo l'eccezione checked
         * @return Oggetto creato
         * @throws X Eccezione checked permessa
         */
        T build() throws X;
    }
}
