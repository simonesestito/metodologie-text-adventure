package com.simonesestito.metodologie.adventure.entita.pojo;

import com.simonesestito.metodologie.adventure.entita.factory.MondoProcessor;
import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Classe immutabile che rappresenta il mondo di gioco
 */
public class Mondo extends Entity {
    private final String description;
    private final Stanza start;

    public Mondo(String name, String description, Stanza start) {
        super(name);
        this.description = description;
        this.start = start;
    }

    public String getDescription() {
        return description;
    }

    public Stanza getStart() {
        return start;
    }

    public static Mondo fromFile(Path file) throws IOException, GameFile.ParseException {
        return new MondoProcessor().parseFromFile(file);
    }
}
