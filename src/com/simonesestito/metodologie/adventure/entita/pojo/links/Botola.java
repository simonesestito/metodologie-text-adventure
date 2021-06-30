package com.simonesestito.metodologie.adventure.entita.pojo.links;

import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Apribile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileConBlocco;
import com.simonesestito.metodologie.adventure.entita.pojo.features.BloccoApertura;

public class Botola extends OggettoLink implements Apribile<BloccoApertura> {
    private final ApribileConBlocco apribileConBlocco = new ApribileConBlocco();

    public Botola(String name, Stanza stanzaB, Stanza stanzaA) {
        super(name, stanzaB, stanzaA);
    }

    @Override
    public void apri() throws AperturaException {
        apribileConBlocco.apri();
    }

    @Override
    public void apri(BloccoApertura oggetto) throws AperturaException {
        apribileConBlocco.apri(oggetto);
    }

    @Override
    public void chiudi(BloccoApertura oggetto) throws ChiusuraException {
        apribileConBlocco.chiudi(oggetto);
    }

    @Override
    public boolean isAperto() {
        return apribileConBlocco.isAperto();
    }

    @Override
    public boolean isAttraversabile() {
        return isAperto();
    }
}
