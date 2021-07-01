package it.uniroma1.textadv.entita.pojo.links;

import it.uniroma1.textadv.entita.pojo.Stanza;
import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.ApribileConBlocco;
import it.uniroma1.textadv.entita.pojo.features.BloccoApertura;

public class Botola extends OggettoLink implements ApribileCon<BloccoApertura> {
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
