package it.uniroma1.textadv.entita.pojo.links;

import it.uniroma1.textadv.entita.pojo.Stanza;
import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.ApribileConChiave;
import it.uniroma1.textadv.entita.pojo.objects.Chiave;

import java.util.Objects;

public class Porta extends OggettoLink implements ApribileCon<Chiave> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Porta porta = (Porta) o;
        return Objects.equals(apertura, porta.apertura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), apertura);
    }
}
