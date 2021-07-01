package it.uniroma1.textadv.entita.factory;

import it.uniroma1.textadv.Mondo;
import it.uniroma1.textadv.entita.parser.GameFile;

import java.util.List;

/**
 * Processor della sezione relativa al mondo all'interno di un file di gioco
 */
@EntityProcessor.ForTag(MondoProcessor.TAG_NAME)
public class MondoProcessor implements EntityProcessor {
    /**
     * Tag identificativo della sezione
     */
    public static final String TAG_NAME = "world";

    /**
     * Chiave della descrizione del mondo
     */
    public static final String DESCRIPTION_LINE_KEY = "description";

    /**
     * Chiave della stanza iniziale del mondo
     */
    public static final String START_ROOM_LINE_KEY = "start";

    /**
     * Elabora la sezione del mondo, e crea le dipendenze tra le entità,
     * generando la nuova entità del mondo
     * @param worldSection Sezione del mondo nel file di gioco
     * @param context Context condiviso tra i processor
     * @throws GameFile.ParseException Errore di elaborazione del contenuto del file (semantica)
     */
    @Override
    public void registerDependencies(GameFile.Section worldSection, BuildContext context) throws GameFile.ParseException {
        var worldName = worldSection.getTag()
                .getArgument()
                .orElseThrow(() -> new GameFile.ParseException("World without name"));

        var worldDescription = worldSection.getLine(DESCRIPTION_LINE_KEY)
                .orElseThrow(() -> new GameFile.ParseException("World without a description"))
                .getArgumentsString();

        var worldStartRoom = worldSection.getLine(START_ROOM_LINE_KEY)
                .orElseThrow(() -> new GameFile.ParseException("World without a start room"))
                .getArgumentsString();

        context.registerDependantEntity(new BuildContext.DependantEntity(
                Mondo.class.getName(),
                worldName,
                List.of(worldStartRoom),
                List.of(worldDescription)
        ));
    }
}
