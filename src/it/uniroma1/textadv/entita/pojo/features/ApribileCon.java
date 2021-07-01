package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

public interface ApribileCon<T> extends UsabileCon<T> {
    default void apri() throws AperturaException {
        apri(null);
    }

    void apri(T oggetto) throws AperturaException;

    default void chiudi(T oggetto) throws ChiusuraException {
        throw new ChiusuraException();
    }

    boolean isAperto();

    @Override
    default void usaCon(T oggetto) throws CommandException {
        apri(oggetto);
    }

    class AperturaException extends CommandException {
        public AperturaException() {
            this("Oh no, non si apre");
        }

        public AperturaException(String message) {
            super(message);
        }
    }

    class ChiusuraException extends AperturaException {
        public ChiusuraException() {
            super("Oh no, non si chiude");
        }
    }
}
