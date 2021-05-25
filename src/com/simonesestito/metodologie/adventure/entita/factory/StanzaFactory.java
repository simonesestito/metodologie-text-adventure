package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.GameFile;
import com.simonesestito.metodologie.adventure.entita.pojo.*;

import java.util.Arrays;
import java.util.stream.Stream;

public class StanzaFactory implements EntityFactory {
    public static final String TAG_NAME = "room";
    public static final String DESCRIPTION_LINE_KEY = "description";
    public static final String OBJECTS_LINE_KEY = "objects";
    public static final String CHARACTERS_LINE_KEY = "characters";
    public static final String LINKS_LINE_KEY = "links";

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
                .forEach(objectName -> context.registerSoftDependency(new BuildContext.SoftDependency(
                        objectName,
                        object -> stanza.addObject((Oggetto) object)
                )));

        section.getLine(CHARACTERS_LINE_KEY)
                .map(GameFile.Line::getCommaSeparatedArguments)
                .orElse(Stream.of())
                .forEach(characterName -> context.registerSoftDependency(new BuildContext.SoftDependency(
                        characterName,
                        character -> stanza.addCharacter((Personaggio) character)
                )));

        section.getLine(LINKS_LINE_KEY)
                .map(l -> l.getArgument(this::parseDirectionsLine))
                .orElse(Stream.of())
                .forEach(linkPair -> context.registerSoftDependency(new BuildContext.SoftDependency(
                        linkPair.linkName(),
                        link -> stanza.addLink((Link) link, linkPair.direction())
                )));

    }

    private Stream<LinkDirectionPair> parseDirectionsLine(String line) {
        return Arrays.stream(line.split(","))
                .map(directionString -> directionString.split(":"))
                .map(directions -> new LinkDirectionPair(
                        Direction.of(directions[0]),
                        directions[1]
                ));
    }

    public static record LinkDirectionPair(
            Direction direction,
            String linkName
    ) {
    }
}
