package it.uniroma1.textadv.entita.pojo.links;

import it.uniroma1.textadv.entita.pojo.Stanza;
import it.uniroma1.textadv.entita.pojo.objects.Oggetto;

import java.util.Objects;

public abstract class OggettoLink extends Oggetto implements Link {
    private final Stanza stanzaA;
    private final Stanza stanzaB;

    public OggettoLink(String name, Stanza stanzaB, Stanza stanzaA) {
        super(name);
        this.stanzaB = stanzaB;
        this.stanzaA = stanzaA;
    }

    @Override
    public Stanza getStanzaA() {
        return stanzaA;
    }

    @Override
    public Stanza getStanzaB() {
        return stanzaB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OggettoLink that = (OggettoLink) o;
        return Objects.equals(getStanzaA(), that.getStanzaA()) && Objects.equals(getStanzaB(), that.getStanzaB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStanzaA(), getStanzaB());
    }
}
