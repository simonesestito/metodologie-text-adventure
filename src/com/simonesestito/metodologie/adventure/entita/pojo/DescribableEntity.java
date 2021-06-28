package com.simonesestito.metodologie.adventure.entita.pojo;

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
}
