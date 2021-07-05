package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

/**
 * Oggetto che può essere rotto.
 *
 * Di conseguenza è usabile con un oggetto che lo può rompere
 */
public interface Rompibile extends UsabileCon<Rompitore> {
    /**
     * Rompi l'oggetto
     * @param rompitore Oggetto che lo rompe
     * @throws CommandException Errore nella sua rottura
     */
    default void rompi(Rompitore rompitore) throws CommandException {
        if (isRotto())
            throw new ObjectBrokenException(toString());

        if (rompitore == null)
            throw new ObjectRequiredException(toString());
    }

    /**
     * Verifica se l'oggetto è rotto
     * @return <code>true</code> se è rotto
     */
    boolean isRotto();

    /**
     * Usa un oggetto che ne rompe altri, sull'oggetto corrente
     * @param oggetto Oggetto che ne rompe altri
     * @throws CommandException Errore nella rottura dell'oggetto attuale
     */
    @Override
    default void usaCon(Rompitore oggetto) throws CommandException {
        rompi(oggetto);
    }

    /**
     * Eccezione in caso serva un oggetto non fornito per rompere quello attuale
     */
    class ObjectRequiredException extends CommandException {
        /**
         * Nuova eccezione con il nome dell'oggetto
         * @param oggetto Oggetto da rompere
         */
        public ObjectRequiredException(String oggetto) {
            super(Strings.of(StringId.UNABLE_TO_BREAK, oggetto));
        }
    }

    /**
     * Eccezione in caso l'oggetto sia già rotto
     */
    class ObjectBrokenException extends CommandException {
        /**
         * Nuova eccezione con il nome dell'oggetto
         * @param oggetto Oggetto da rompere
         */
        public ObjectBrokenException(String oggetto) {
            super(Strings.of(StringId.OBJECT_BROKEN, oggetto));
        }
    }

    /**
     * Eccezione in caso sia eseguita un'azione che richiede la rottura,
     * ma si segnala che l'oggetto non è ancora rotto.
     */
    class ObjectUnbrokenException extends CommandException {
        /**
         * Nuova eccezione con il nome dell'oggetto
         * @param oggetto Oggetto da rompere
         */
        public ObjectUnbrokenException(String oggetto) {
            super(Strings.of(StringId.OBJECT_UNBROKEN, oggetto));
        }
    }
}
