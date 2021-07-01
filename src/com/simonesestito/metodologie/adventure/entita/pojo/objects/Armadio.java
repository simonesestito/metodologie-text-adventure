package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileCon;

import java.util.List;

public class Armadio extends OggettoContenitore implements ApribileCon<Tronchesi> {
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
