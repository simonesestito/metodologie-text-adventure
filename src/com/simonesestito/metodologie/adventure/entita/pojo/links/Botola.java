package com.simonesestito.metodologie.adventure.entita.pojo.links;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Apribile;

public class Botola extends OggettoLink implements Apribile {
    private boolean aperto = false;

    public Botola(String name, Stanza stanzaB, Stanza stanzaA) {
        super(name, stanzaB, stanzaA);
    }

    @Override
    public void apri() throws CommandException {
        // TODO: Aprire con qualcosa
        throw new CommandException("La botola è chiusa");
    }

    @Override
    public boolean isAperto() {
        return aperto;
    }

    @Override
    public boolean isAttraversabile() {
        return isAperto();
    }
}
