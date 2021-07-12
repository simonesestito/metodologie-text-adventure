package it.uniroma1.textadv.entity.pojo.objects;

import java.util.Objects;

/**
 * Tronchesi usate per rompere un armadio
 */
public class Tronchesi extends Oggetto {
    /**
     * Armadio che vanno a rompere
     */
    private final Armadio armadio;

    /**
     * Crea una tronchesi specifica per un armadio
     *
     * @param name    Nome della tronchesi
     * @param armadio Armadio in grado di rompere
     */
    public Tronchesi(String name, Armadio armadio) {
        super(name);
        this.armadio = armadio;
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due tronchesi uguali
     *
     * @param o Altro oggetto
     * @return <code>true</code> se sono due tronchesi uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tronchesi tronchesi = (Tronchesi) o;
        return Objects.equals(armadio, tronchesi.armadio);
    }

    /**
     * Calcola l'hash dell'oggetto
     *
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), armadio);
    }
}
