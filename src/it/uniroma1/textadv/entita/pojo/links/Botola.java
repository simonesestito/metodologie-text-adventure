package it.uniroma1.textadv.entita.pojo.links;

import it.uniroma1.textadv.entita.pojo.Giocatore;
import it.uniroma1.textadv.entita.pojo.Stanza;
import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.ApribileConBlocco;
import it.uniroma1.textadv.entita.pojo.features.BloccoApertura;

import java.util.Objects;

public class Botola extends OggettoLink implements ApribileCon<BloccoApertura> {
    private final ApribileConBlocco apribileConBlocco = new ApribileConBlocco();

    public Botola(String name, Stanza stanzaB, Stanza stanzaA) {
        super(name, stanzaB, stanzaA);
    }

    @Override
    public void apri(BloccoApertura oggetto) throws AperturaException {
        apribileConBlocco.apri(oggetto);
        Giocatore.getInstance().rispondiUtente("""
                                          _.-'
                                        _.-'
                        _____________.-'________________
                       /         _.-' O                /|
                      /  i====_======O      __________/ /
                     /  / _.-'      O      /     _   /|/
                    /  / | p       o      /     (   / /
                   /  /           O      /_________/ /
                  /  L===========O                /|/
                 /______________O________________/ /
                |________________________________|/
                """);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Botola botola = (Botola) o;
        return Objects.equals(apribileConBlocco, botola.apribileConBlocco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apribileConBlocco);
    }
}
