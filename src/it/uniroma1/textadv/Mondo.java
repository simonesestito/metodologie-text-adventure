package it.uniroma1.textadv;

import it.uniroma1.textadv.entity.factory.MondoFactory;
import it.uniroma1.textadv.entity.parser.GameFile;
import it.uniroma1.textadv.entity.pojo.DescribableEntity;
import it.uniroma1.textadv.entity.pojo.Stanza;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

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

    /**
     * Metodo statico factory per la creazione di un mondo da un dato file che lo descrive.
     *
     * @param file File che descrive il mondo da istanziare
     * @return Mondo istanziato
     * @throws IOException             Errore nella lettura del file
     * @throws GameFile.ParseException Errore nel parsing del file
     */
    public static Mondo fromFile(String file) throws IOException, GameFile.ParseException {
        return Mondo.fromFile(Path.of(file));
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due mondi uguali
     * @param o Altro oggetto
     * @return <code>true</code> se sono due mondi uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Mondo mondo = (Mondo) o;
        return Objects.equals(getStart(), mondo.getStart());
    }

    /**
     * Calcola l'hash del mondo di gioco
     * @return hash del mondo
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStart());
    }
}
