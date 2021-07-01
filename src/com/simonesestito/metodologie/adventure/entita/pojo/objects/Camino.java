package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.features.UsabileCon;

import java.util.List;

public class Camino extends OggettoContenitore implements UsabileCon<Secchio> {
    private boolean acceso = true;

    public Camino(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    public boolean isAcceso() {
        return acceso;
    }

    private void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    public void accendi() {
        setAcceso(true);
    }

    public void spegni() {
        setAcceso(false);
    }

    @Override
    public void prendiOggetto(Oggetto oggetto) throws CommandException {
        if (acceso)
            throw new CommandException("Il camino è ancora acceso");
        super.prendiOggetto(oggetto);
    }

    @Override
    public void usaCon(Secchio secchio) {
        if (secchio.isRiempito())
            spegni();
    }
}
