package it.uniroma1.textadv.entita.pojo;

import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.Objects;

/**
 * Entità più generica di tutti gli elementi di gioco.
 * <p>
 * Serve per tenere un comun denominatore tra ogni possibile elemento di gioco.
 */
public abstract class Entity {
    /**
     * Nome dell'elemento di gioco
     */
    private final String name;

    /**
     * Crea un nuovo elemento di gioco avendo il suo nome
     *
     * @param name Nome del nuovo elemento
     */
    public Entity(String name) {
        this.name = name;
    }

    /**
     * Ottieni il nome dell'elemento di gioco
     *
     * @return Nome dell'elemento corrente
     */
    public String getName() {
        return name;
    }

    /**
     * Rappresenta un generico elemento di gioco come stringa in base al suo nome
     *
     * @return Rappresentazione a stringa dell'elemento
     */
    @Override
    public String toString() {
        if (this instanceof ApribileCon<?>) {
            return ((ApribileCon<?>) this).isAperto()
                    ? Strings.of(StringId.ENTITY_OPEN, getName())
                    : Strings.of(StringId.ENTITY_CLOSED, getName());
        } else {
            return getName();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(getName(), entity.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
