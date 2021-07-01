package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileCon;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileSemplice;

import java.util.List;

public class Scrivania extends OggettoContenitore implements ApribileCon<Object> {
    private final ApribileSemplice apribileSemplice = new ApribileSemplice();

    public Scrivania(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    @Override
    public void apri() throws AperturaException {
        apribileSemplice.apri();
    }

    @Override
    public void apri(Object oggetto) throws AperturaException {
        apribileSemplice.apri(oggetto);
    }

    @Override
    public void chiudi(Object oggetto) throws ChiusuraException {
        apribileSemplice.chiudi(oggetto);
    }

    @Override
    public boolean isAperto() {
        return apribileSemplice.isAperto();
    }

    @Override
    public void prendiOggetto(Oggetto oggetto) throws CommandException {
        if (!isAperto())
            throw new CommandException("La scrivania è chiusa");
        super.prendiOggetto(oggetto);
    }
}
