package com.simonesestito.metodologie.adventure.entita.pojo;

import com.simonesestito.metodologie.adventure.entita.factory.MondoFactory;
import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Classe immutabile che rappresenta il mondo di gioco
 */
public class Mondo extends DescribableEntity {
    /**
     * Stanza da cui inizia
     */
    private final Stanza start;

    /**
     * Crea un nuovo mondo dati i suoi parametri fondamentali
     *
     * @param name        Nome del mondo
     * @param description Descrizione testuale
     * @param start       Stanza da cui iniziare
     */
    public Mondo(String name, String description, Stanza start) {
        super(name, description);
        this.start = start;
    }

    /**
     * Ottieni la stanza da cui inizia il mondo
     *
     * @return Inizio del mondo
     */
    public Stanza getStart() {
        return start;
    }

    /**
     * Metodo statico factory per la creazione di un mondo da un dato file che lo descrive.
     *
     * @param file File che descrive il mondo da istanziare
     * @return Mondo istanziato
     * @throws IOException             Errore nella lettura del file
     * @throws GameFile.ParseException Errore nel parsing del file
     */
    public static Mondo fromFile(Path file) throws IOException, GameFile.ParseException {
        return new MondoFactory().parseFromFile(file);
    }
}
