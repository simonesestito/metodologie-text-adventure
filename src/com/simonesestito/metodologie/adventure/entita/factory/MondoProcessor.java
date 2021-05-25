package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.pojo.Mondo;
import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Crea un nuovo mondo avendo un dato file come sorgente
 */
@EntityProcessor.ForTag(MondoProcessor.TAG_NAME)
public class MondoProcessor implements EntityProcessor {
    public static final String TAG_NAME = "world";
    public static final String DESCRIPTION_LINE_KEY = "description";
    public static final String START_ROOM_LINE_KEY = "start";

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
                Mondo.class.getSimpleName(),
                worldName,
                List.of(worldStartRoom),
                List.of(worldDescription)
        ));
    }
}
