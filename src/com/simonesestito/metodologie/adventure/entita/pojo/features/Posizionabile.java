package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.CommandException;

import java.util.Optional;

public interface Posizionabile {
    Contenitore getPosizione();

    void spostaIn(Contenitore contenitore) throws CommandException;

    default Optional<ContenitoreAggiungibile> getPosizioneAggiungibile() {
        var posizione = getPosizione();
        if (posizione instanceof ContenitoreAggiungibile)
            return Optional.of((ContenitoreAggiungibile) posizione);
        else
            return Optional.empty();
    }
}
