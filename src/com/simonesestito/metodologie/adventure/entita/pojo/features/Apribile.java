package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.CommandException;

public interface Apribile<T> {
    default void apri() throws AperturaException {
        apri(null);
    }

    void apri(T oggetto) throws AperturaException;
    void chiudi(T oggetto) throws ChiusuraException;
    boolean isAperto();

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
