package it.uniroma1.textadv.entity.pojo;

import java.util.Objects;

/**
 * Entità che fornisce anche una descrizione
 */
public class DescribableEntity extends Entity {
    /**
     * Descrizione dell'entità
     */
    private final String description;

    /**
     * Crea un nuovo elemento di gioco avendo il suo nome
     *
     * @param name        Nome del nuovo elemento
     * @param description Descrizione dell'elemento
     */
    public DescribableEntity(String name, String description) {
        super(name);
        this.description = description;
    }

    /**
     * Ottieni la descrizione dell'entità
     *
     * @return Descrizione dell'entità
     */
    public String getDescription() {
        return description;
    }

    /**
     * Rappresentazione come stringa dell'entità, includendo la descrizione fornita
     *
     * @return Rappresentazione testuale
     */
    @Override
    public String toString() {
        return super.toString() + " (" + description + ")";
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due entità uguali
     *
     * @param o Altro oggetto
     * @return <code>true</code> se sono due entità uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DescribableEntity that = (DescribableEntity) o;
        return Objects.equals(getDescription(), that.getDescription());
    }

    /**
     * Calcola l'hash in base anche alla descrizione
     *
     * @return Hash calcolato
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescription());
    }
}
