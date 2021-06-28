package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Apribile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class OggettoContenitore extends Oggetto implements Contenitore {
    private final List<Oggetto> contenuto;

    public OggettoContenitore(String name, List<Oggetto> contenuto) {
        super(name);
        this.contenuto = new ArrayList<>(contenuto);
    }

    @Override
    public List<Oggetto> getOggettiContenuti() {
        return Collections.unmodifiableList(contenuto);
    }

    @Override
    public void prendiOggetto(Oggetto oggetto) throws TextEngine.CommandException {
        contenuto.remove(oggetto);
    }

    @Override
    public String toString() {
        String contentDescription;
        if (this instanceof Apribile && !((Apribile) this).isAperto()) {
            contentDescription = "c'è qualcosa dentro";
        } else if (((Contenitore) this).getOggettiContenuti().isEmpty()) {
            contentDescription = "dentro non c'è nulla";
        } else {
            contentDescription = "dentro c'è: " + ((Contenitore) this).getOggettiContenuti()
                    .stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining(", "));
        }
        return super.toString() + ", " + contentDescription;
    }
}
