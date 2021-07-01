package com.simonesestito.metodologie.adventure.entita.pojo;

import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileCon;

import java.util.LinkedList;
import java.util.List;

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
        List<String> descriptionParts = new LinkedList<>();
        descriptionParts.add(getName());

        if (this instanceof ApribileCon<?>) {
            descriptionParts.add(
                    ((ApribileCon<?>) this).isAperto()
                            ? "è aperto"
                            : "è chiuso"
            );
        }

        return String.join(", ", descriptionParts);
    }
}
