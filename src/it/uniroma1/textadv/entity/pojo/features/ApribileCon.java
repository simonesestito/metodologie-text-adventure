package it.uniroma1.textadv.entity.pojo.features;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

/**
 * Entità che può essere aperta con qualcosa
 *
 * @param <T> Tipo dell'oggetto che la apre
 */
public interface ApribileCon<T> extends UsabileCon<T> {
    /**
     * Apri l'oggetto senza alcun altro oggetto
     *
     * @throws AperturaException Errore nell'apertura dell'oggetto
     */
    default void apri() throws AperturaException {
        apri(null);
    }

    /**
     * Apri l'oggetto con un altro oggetto dato
     *
     * @param oggetto Oggetto con cui aprire
     * @throws AperturaException Errore nell'apertura dell'oggetto
     */
    void apri(T oggetto) throws AperturaException;

    /**
     * Chiudi un oggetto con un altro oggetto
     *
     * @param oggetto Oggetto con cui chiudere
     * @throws ChiusuraException rrore nella chiusura dell'oggetto
     */
    default void chiudi(T oggetto) throws ChiusuraException {
        throw new ChiusuraException();
    }

    /**
     * Controlla se l'oggetto è attualmente aperto
     *
     * @return <code>true</code> se è aperto
     */
    boolean isAperto();

    /**
     * Un oggetto apribile può essere usato con un altro.
     * Equivale ad aprire l'oggetto corrente con quello passato in input.
     *
     * @param oggetto Oggetto da usare, ovvero con cui aprire
     * @throws AperturaException Errore nell'apertura dell'oggetto
     */
    @Override
    default void usaCon(T oggetto) throws AperturaException {
        apri(oggetto);
    }

    /**
     * Errore nell'apertura dell'oggetto
     */
    class AperturaException extends CommandException {
        /**
         * Errore di apertura generico
         */
        public AperturaException() {
            this(Strings.of(StringId.UNABLE_TO_OPEN));
        }

        /**
         * Errore di apertura con un messaggio dato
         *
         * @param message Messaggio d'errore
         */
        public AperturaException(String message) {
            super(message);
        }
    }

    /**
     * Errore nella chiusura dell'oggetto
     */
    class ChiusuraException extends AperturaException {
        /**
         * Errore di chiusura generico
         */
        public ChiusuraException() {
            super(Strings.of(StringId.UNABLE_TO_CLOSE));
        }
    }
}
