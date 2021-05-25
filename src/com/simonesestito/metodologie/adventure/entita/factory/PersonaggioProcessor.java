package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

/**
 * Processor per la sezione relativa ai vari personaggi nel gioco
 */
@EntityProcessor.ForTag(PersonaggioProcessor.TAG_NAME)
public class PersonaggioProcessor implements EntityProcessor {
    /**
     * Tag relativo alla sezione nel file di gioco
     */
    public static final String TAG_NAME = "characters";

    /**
     * Elabora la sezione dei personaggi, creando un'istanza per ognuno di essi.
     *
     * @param section Sezione da processare
     * @param context Context condiviso tra i processor
     * @throws GameFile.ParseException Errore di elaborazione della sezione di gioco
     */
    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        for (var line : section) {
            var name = line.getKey();
            var className = line.getArgumentsString();
            context.registerDependantEntity(
                    new BuildContext.DependantEntity(className, name)
            );
        }
    }
}
