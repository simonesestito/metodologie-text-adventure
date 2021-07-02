package it.uniroma1.textadv.entita.pojo;

import java.util.Objects;

/**
 * Entità che fornisce anche una descrizione
 */
public class DescribableEntity extends Entity {
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

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + description + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DescribableEntity that = (DescribableEntity) o;
        return Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescription());
    }
}
