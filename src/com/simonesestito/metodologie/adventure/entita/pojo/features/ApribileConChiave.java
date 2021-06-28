package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Chiave;

public interface ApribileConChiave extends Apribile {
    @Override
    default void apri() throws CommandException {
        throw new CommandException("Non si può aprire senza chiave");
    }

    void apri(Chiave chiave) throws ChiaveErrataException;

    class ChiaveErrataException extends CommandException {
        public ChiaveErrataException() {
            super("Chiave errata per questo oggetto");
        }
    }
}
