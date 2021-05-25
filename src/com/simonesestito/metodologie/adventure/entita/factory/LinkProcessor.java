package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.util.List;

/**
 * Processor in grado di elaborare la sezione
 * relativa agli oggetti che fungono da link
 * tra 2 stanze, che quindi saranno collegate tra loro.
 */
@EntityProcessor.ForTag(LinkProcessor.TAG_NAME)
public class LinkProcessor implements EntityProcessor {
    /**
     * Tag identificativo della sezione dei link
     */
    public static final String TAG_NAME = "links";

    /**
     * Trasforma ogni linea della sezione in un link
     * dipendente dalle stanze che collega
     *
     * @param section Sezione da processare
     * @param context Context condiviso tra i processor
     * @throws GameFile.ParseException Errori di elaborazione della sezione
     */
    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        for (var line : section) {
            var name = line.getKey();
            var args = line.getSeparatedArguments().iterator();
            var className = args.next();
            var roomAName = args.next();
            var roomBName = args.next();
            context.registerDependantEntity(new BuildContext.DependantEntity(
                    className, name, List.of(roomAName, roomBName)
            ));
        }
    }
}
