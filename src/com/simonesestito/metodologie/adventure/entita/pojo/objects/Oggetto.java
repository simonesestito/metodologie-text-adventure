package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.Entity;
import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ContenitoreAggiungibile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Posizionabile;

import java.util.Objects;
import java.util.Optional;

public abstract class Oggetto extends Entity implements Posizionabile {
    private Contenitore posizione;

    public Oggetto(String name) {
        super(name);
    }

    public void spostaIn(Contenitore contenitore) throws CommandException {
        if (Objects.equals(contenitore, posizione)) {
            System.out.println("Inutile spostare da " + contenitore + " a " + posizione);
            return;
        }

        if (this.posizione != null)
            this.posizione.prendiOggetto(this);
        this.posizione = contenitore;

        System.out.println("getPosizioneAggiungibile() = " + getPosizioneAggiungibile());
        
        getPosizioneAggiungibile().ifPresent(s -> s.aggiungiOggetto(this));
    }

    @Override
    public Contenitore getPosizione() {
        return posizione;
    }
}
