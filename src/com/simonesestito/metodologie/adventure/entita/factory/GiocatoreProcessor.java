package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.util.List;

@EntityProcessor.ForTag("player")
public class GiocatoreProcessor implements EntityProcessor {
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
}
