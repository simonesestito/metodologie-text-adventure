package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;

import java.util.List;

public class Cassetto extends OggettoContenitore {

    public Cassetto(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }
}
