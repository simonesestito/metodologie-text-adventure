package com.simonesestito.metodologie.adventure.entita.pojo.links;

import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Apribile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileConChiave;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Chiave;

public class Porta extends OggettoLink implements Apribile<Chiave> {
    private final ApribileConChiave apertura = new ApribileConChiave();

    public Porta(String name, Stanza stanzaB, Stanza stanzaA) {
        super(name, stanzaB, stanzaA);
    }

    @Override
    public void apri() throws AperturaException {
        apertura.apri();
    }

    @Override
    public void apri(Chiave oggetto) throws AperturaException {
        apertura.apri(oggetto);
    }

    @Override
    public void chiudi(Chiave oggetto) throws ChiusuraException {
        apertura.chiudi(oggetto);
    }

    @Override
    public boolean isAperto() {
        return apertura.isAperto();
    }

    @Override
    public boolean isAttraversabile() {
        return isAperto();
    }
}
