package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.entita.pojo.features.Apribile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;

import java.util.List;

public class Armadio extends OggettoContenitore implements Apribile<Tronchesi> {
    private boolean aperto = true;

    public Armadio(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    @Override
    public void apri(Tronchesi oggetto) throws AperturaException {
        if (oggetto != null)
            aperto = true;
    }

    @Override
    public boolean isAperto() {
        return aperto;
    }
}
