package it.uniroma1.textadv.entita.factory;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.parser.GameFile;
import it.uniroma1.textadv.entita.pojo.Stanza;
import it.uniroma1.textadv.entita.pojo.characters.Personaggio;
import it.uniroma1.textadv.entita.pojo.links.Direction;
import it.uniroma1.textadv.entita.pojo.links.Link;
import it.uniroma1.textadv.entita.pojo.objects.Oggetto;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Processor per l'elaborazione delle stanze nel gioco
 */
@EntityProcessor.ForTag(StanzaProcessor.TAG_NAME)
public class StanzaProcessor implements EntityProcessor {
    /**
     * Tag della sezione delle stanze nel file
     */
    public static final String TAG_NAME = "room";

    /**
     * Chiave della linea della descrizione della stanza
     */
    public static final String DESCRIPTION_LINE_KEY = "description";

    /**
     * Chiave della linea degli oggetti della stanza
     */
    public static final String OBJECTS_LINE_KEY = "objects";

    /**
     * Chiave della linea dei personaggi della stanza
     */
    public static final String CHARACTERS_LINE_KEY = "characters";

    /**
     * Chiave della linea dei collegamenti con altre stanze o oggetti {@link Link}
     */
    public static final String LINKS_LINE_KEY = "links";

    /**
     * Elabora la sezione della stanza.
     * <p>
     * Le stanze vengono create senza dipendenze forti,
     * successivamente verrà aggiunto il resto tramite dipendenze leggere.
     * Evita le dipendenze circolari forti.
     *
     * @param section Sezione da processare
     * @param context Context condiviso tra i processor
     * @throws GameFile.ParseException Errore di elaborazione della sezione
     */
    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        var name = section.getTag()
                .getArgument()
                .orElseThrow(() -> new GameFile.ParseException("Room without a name found"));

        var description = section.getLine(DESCRIPTION_LINE_KEY)
                .map(GameFile.Line::getArgumentsString)
                .orElseThrow(() -> new GameFile.ParseException("Room without a description found"));

        var stanza = new Stanza(name, description);
        context.addResolvedDependency(stanza);

        section.getLine(OBJECTS_LINE_KEY)
                .map(GameFile.Line::getCommaSeparatedArguments)
                .orElse(Stream.of())
                .forEach(objectName -> context.observeEntity(new BuildContext.DependencyObserver(
                        objectName,
                        object -> {
                            try {
                                ((Oggetto) object).spostaIn(stanza);
                            } catch (CommandException e) {
                                throw new IllegalStateException(e);
                            }
                        }
                )));

        section.getLine(CHARACTERS_LINE_KEY)
                .map(GameFile.Line::getCommaSeparatedArguments)
                .orElse(Stream.of())
                .forEach(characterName -> context.observeEntity(new BuildContext.DependencyObserver(
                        characterName,
                        character -> stanza.addCharacter((Personaggio) character)
                )));

        section.getLine(LINKS_LINE_KEY)
                .map(l -> l.getArgument(this::parseDirectionsLine))
                .orElse(Stream.empty())
                .forEach(linkPair -> context.observeEntity(new BuildContext.DependencyObserver(
                        linkPair.linkName(),
                        providedLink -> {
                            var link = providedLink instanceof Link
                                    ? (Link) providedLink
                                    : Link.createDirect(stanza, (Stanza) providedLink);
                            stanza.addLink(link, linkPair.direction());
                        }
                )));
    }

    /**
     * Esegui il parsing della linea delle direzioni dei link,
     * formato particolare non presente altrove.
     *
     * @param line Linea da parsare
     * @return Stream dei link con le direzioni
     */
    private Stream<LinkDirectionPair> parseDirectionsLine(String line) {
        return Arrays.stream(line.split(","))
                .map(directionString -> directionString.split(":"))
                .map(directions -> new LinkDirectionPair(
                        Direction.of(directions[0]).orElseThrow(),
                        directions[1]
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

    /**
     * Record per rappresentare un link con la sua direzione.
     * <p>
     * La rappresentazione della stringa è mediante nome della dipendenza,
     * ancora da risolvere in fase preliminare.
     */
    private static record LinkDirectionPair(
            Direction direction,
            String linkName
    ) {
    }
}
