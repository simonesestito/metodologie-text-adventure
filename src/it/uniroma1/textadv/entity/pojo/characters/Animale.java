package it.uniroma1.textadv.entity.pojo.characters;

import it.uniroma1.textadv.entity.pojo.Giocatore;
import it.uniroma1.textadv.entity.pojo.features.Accarezzabile;
import it.uniroma1.textadv.entity.pojo.features.Parla;

import java.util.Objects;

/**
 * Rappresenta un generico animale all'interno del gioco
 */
public abstract class Animale extends Personaggio implements Accarezzabile, Parla {
    /**
     * Crea un nuovo animale con un nome
     * @param name Nome dell'animale
     */
    public Animale(String name) {
        super(name);
    }

    /**
     * Ottieni il verso dell'animale
     * @return Verso dell'animale
     */
    public abstract String getVerso();

    /**
     * Accarezza questo animale
     */
    @Override
    public void accarezza() {
        Giocatore.getInstance().rispondiUtente(getVerso());
    }

    /**
     * Parla con l'animale
     * @return Verso dell'animale
     */
    @Override
    public String parla() {
        return getVerso();
    }

    /**
     * Controlla se due animali sono uguali
     * @param o Altro oggetto da controllare
     * @return <code>true</code> se gli animali sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        if (!super.equals(o)) return false;
        Animale animale = (Animale) o;
        return Objects.equals(animale.getVerso(), getVerso());
    }

    /**
     * Calcola l'hash dell'oggetto
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getVerso());
    }
}
