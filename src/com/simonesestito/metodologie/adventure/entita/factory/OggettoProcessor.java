package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.util.List;

/**
 * Processor per gli oggetti del mondo, descritti nel file di gioco
 */
@EntityProcessor.ForTag(OggettoProcessor.TAG_NAME)
public class OggettoProcessor implements EntityProcessor {
    /**
     * Tag della sezione relativa
     */
    public static final String TAG_NAME = "objects";

    /**
     * Elabora la sezione degli oggetti, gestendo gli oggetti indipendenti
     * e quelli che dipendono da un altro oggetto sul quale vengono usati.
     *
     * @param section Sezione da processare
     * @param context Context condiviso tra i processor
     * @throws GameFile.ParseException Errore di elaborazione della sezione
     */
    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        for (var line : section) {
            var name = line.getKey();
            var args = line.getSeparatedArguments().iterator();
            var className = args.next();
            var subject = args.hasNext() ? args.next() : null;

            context.registerDependantEntity(new BuildContext.DependantEntity(
                    className,
                    name,
                    subject == null ? List.of() : List.of(subject)
            ));
        }
    }
}
