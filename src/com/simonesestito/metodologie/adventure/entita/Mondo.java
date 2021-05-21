package com.simonesestito.metodologie.adventure.entita;

/**
 * Classe immutabile che rappresenta il mondo di gioco
 */
public class Mondo {
    private final String name;
    private final String description;
    private final Stanza start;

    public Mondo(String name, String description, Stanza start) {
        this.name = name;
        this.description = description;
        this.start = start;
    }
}
