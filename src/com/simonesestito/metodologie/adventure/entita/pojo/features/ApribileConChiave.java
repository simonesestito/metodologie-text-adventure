package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Chiave;

public interface ApribileConChiave extends Apribile {
    @Override
    default void apri() throws TextEngine.CommandException {
        throw new TextEngine.CommandException("Non si può aprire senza chiave");
    }

    void apri(Chiave chiave) throws ChiaveErrataException;

    class ChiaveErrataException extends TextEngine.CommandException {
        public ChiaveErrataException() {
            super("Chiave errata per questo oggetto");
        }
    }
}
