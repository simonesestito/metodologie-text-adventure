package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Apribile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scrivania extends OggettoContenitore implements Apribile {
    private boolean aperto = false;

    public Scrivania(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    @Override
    public void apri() {
        aperto = true;
    }

    @Override
    public boolean isAperto() {
        return aperto;
    }

    @Override
    public void prendiOggetto(Oggetto oggetto) throws TextEngine.CommandException {
        if (!isAperto())
            throw new TextEngine.CommandException("La scrivania è chiusa");
        super.prendiOggetto(oggetto);
    }
}
