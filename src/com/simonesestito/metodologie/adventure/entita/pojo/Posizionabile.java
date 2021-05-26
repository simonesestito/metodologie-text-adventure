package com.simonesestito.metodologie.adventure.entita.pojo;

import com.simonesestito.metodologie.adventure.engine.TextEngine;

public interface Posizionabile {
    Stanza getCurrentLocation();
    void moveTo(Stanza stanza);

    default void moveTo(Direction direction) throws TextEngine.CommandException {
        var destination = getCurrentLocation().getLink(direction)
                .map(l -> l.getDestinazione(getCurrentLocation()))
                .orElseThrow(() -> new TextEngine.CommandException("Destinazione non trovata"));
        moveTo(destination);
    }
}
