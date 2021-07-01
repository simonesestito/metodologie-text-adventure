package it.uniroma1.textadv.entita.factory;

import it.uniroma1.textadv.entita.parser.GameFile;
import it.uniroma1.textadv.Mondo;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Factory vera e propria per la creazione del mondo intero,
 * a partire da un dato file in input.
 * <p>
 * Fa uso dei vari {@link EntityProcessor} per elaborare le varie parti del file.
 * Questa è la factory che crea il mondo, ma i vari {@link EntityProcessor}
 * possono essere considerati come "sotto-factory", che lavorando assieme,
 * creano il mondo permettendo alla factory {@link MondoFactory} di
 * realizzare il proprio compito.
 */
public class MondoFactory {
    /**
     * Crea un mondo dato un file, che dovrà rispettare il formato del gioco.
     *
     * @param file File da cui leggere i dati del mondo
     * @return Mondo creato come descritto nel file
     * @throws GameFile.ParseException Errore di elaborazione del file nei suoi contenuti
     * @throws IOException             Errore di I/O relativo al file dato in input
     */
    public Mondo parseFromFile(Path file) throws GameFile.ParseException, IOException {
        var gameFile = GameFile.parseFile(file);
        var context = new EntityProcessor.BuildContext(gameFile);

        for (GameFile.Section section : gameFile) {
            EntityProcessor
                    .forTag(section.getTag().getName())
                    .registerDependencies(section, context);
        }

        return context.resolve()
                .getResolvedDependency(Mondo.class)
                .orElseThrow(() -> new GameFile.ParseException("World not found in this file"));
    }
}
