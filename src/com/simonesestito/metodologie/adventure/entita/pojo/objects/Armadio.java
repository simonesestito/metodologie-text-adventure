package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;

import java.util.List;

public class Armadio extends OggettoContenitore {
    public Armadio(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }
}
