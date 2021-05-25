package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.pojo.Mondo;
import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Crea un nuovo mondo avendo un dato file come sorgente
 */
public class MondoFactory implements EntityFactory {
    public static final String TAG_NAME = "world";
    public static final String DESCRIPTION_LINE_KEY = "description";
    public static final String START_ROOM_LINE_KEY = "start";

    public Mondo parseFromFile(Path file) throws GameFile.ParseException, IOException {
        var gameFile = GameFile.parseFile(file);
        var context = new BuildContext(gameFile);

        for (GameFile.Section section : gameFile) {
            EntityFactory
                    .forTag(section.getTag().getName())
                    .registerDependencies(section, context);
        }

        return context.resolve()
                .getResolvedDependency(Mondo.class)
                .orElseThrow(() -> new GameFile.ParseException("World not found in this file"));
    }

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

        context.registerHardDependency(new BuildContext.HardDependency(
                Mondo.class.getSimpleName(),
                worldName,
                List.of(worldName, worldDescription),
                List.of(worldStartRoom)
        ));
    }
}
