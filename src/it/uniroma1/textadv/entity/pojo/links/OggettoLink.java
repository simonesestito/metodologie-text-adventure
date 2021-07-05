package it.uniroma1.textadv.entity.pojo.links;

import it.uniroma1.textadv.entity.pojo.Stanza;
import it.uniroma1.textadv.entity.pojo.objects.Oggetto;

import java.util.Objects;

/**
 * Oggetto che opera come collegamento tra due stanze
 */
public abstract class OggettoLink extends Oggetto implements Link {
    /**
     * Stanza collegata
     */
    private final Stanza stanzaA;

    /**
     * Altra stanza collegata
     */
    private final Stanza stanzaB;

    /**
     * Crea un oggetto che collega due stanze
     * @param name Nome dell'oggetto
     * @param stanzaA Stanza collegata
     * @param stanzaB Altra stanza collegata
     */
    public OggettoLink(String name, Stanza stanzaA, Stanza stanzaB) {
        super(name);
        this.stanzaA = stanzaA;
        this.stanzaB = stanzaB;
    }

    /**
     * Ottieni una delle due stanze collegate
     * @return Stanza collegata
     */
    @Override
    public Stanza getStanzaA() {
        return stanzaA;
    }

    /**
     * Ottieni una delle due stanze collegate
     * @return Stanza collegata
     */
    @Override
    public Stanza getStanzaB() {
        return stanzaB;
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due link uguali
     * @param o Altro oggetto
     * @return <code>true</code> se sono due link uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OggettoLink that = (OggettoLink) o;
        return Objects.equals(getStanzaA(), that.getStanzaA()) && Objects.equals(getStanzaB(), that.getStanzaB());
    }

    /**
     * Calcola l'hash di un collegamento in base alle stanze che collega
     * @return Hash delle stanze collegate
     */
    @Override
    public int hashCode() {
        return Objects.hash(getStanzaA().getName(), getStanzaB().getName());
    }
}
