package it.uniroma1.textadv.entita.factory;

import it.uniroma1.textadv.entita.parser.GameFile;

import java.util.List;

/**
 * Processor in grado di elaborare i giocatori.
 * <p>
 * Si asserisce sia uno solo, e deve essere disponibile a tutti.
 */
@EntityProcessor.ForTag(GiocatoreProcessor.TAG_NAME)
public class GiocatoreProcessor implements EntityProcessor {
    /**
     * Tag della sezione del giocatore
     */
    public static final String TAG_NAME = "player";

    /**
     * Elabora la sezione del giocatore.
     * <p>
     * Valida che sia uno solo.
     *
     * @param section Sezione da processare
     * @param context Context condiviso tra i processor
     * @throws GameFile.ParseException Errori di elaborazione della sezione
     */
    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        if (section.getLines().size() > 1)
            throw new GameFile.ParseException("Multiple players found");

        if (section.getLines().isEmpty())
            throw new GameFile.ParseException("Player not found");

        var playerLine = section.getLines().iterator().next();
        var name = playerLine.getKey();
        var className = playerLine.getArgumentsString();

        var worldName = context.getGameFile()
                .getSectionByTag(MondoProcessor.TAG_NAME)
                .orElseThrow()
                .getTag()
                .getArgument()
                .orElseThrow();

        context.registerDependantEntity(new BuildContext.DependantEntity(
                className, name, List.of(worldName)
        ));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj != null && obj.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
