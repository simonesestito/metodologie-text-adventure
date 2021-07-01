package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.entita.pojo.Entity;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;

import java.util.Objects;
import java.util.Optional;

public abstract class Oggetto extends Entity {
    private Contenitore posizione;

    public Oggetto(String name) {
        super(name);
    }

    /*
    @Override

    public void spostaIn(Contenitore contenitore) {
        if (Objects.equals(contenitore, posizione))
            return;

        if (this.posizione != null)
            this.posizione.prendiOggetto(this);
        this.posizione = contenitore;

        if (contenitore == null) {
            getStanza().ifPresent(s -> s.addObject(this));
        }
    }

    @Override
    public Optional<Contenitore<? super Spostabile>> getPosizione() {
        return Optional.ofNullable(posizione);
    }

    @Override
    public void spostaIn(Contenitore<?> stanza) {
        if (stanza instanceof Contenitore<? super Spostabile> && !Objects.equals(stanza, this.posizione)) {
            this.posizione = (Contenitore<? super Spostabile>) stanza;
        }
    }

    public Optional<Contenitore<? super Spostabile>> getStanza() {
        return getPosizione().map(p -> p instanceof Stanza)
    }

     */
}
